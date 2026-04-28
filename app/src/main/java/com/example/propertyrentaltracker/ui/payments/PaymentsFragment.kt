package com.example.propertyrentaltracker.ui.payments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.propertyrentaltracker.databinding.DialogAddPaymentBinding
import com.example.propertyrentaltracker.databinding.FragmentPaymentsBinding
import com.example.propertyrentaltracker.utils.PdfHelper
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PaymentsFragment : Fragment() {

    private var _binding: FragmentPaymentsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PaymentViewModel by viewModels()
    private lateinit var paymentAdapter: PaymentAdapter

    // TODO: For a complete app, we'd select a tenant first. For now, defaulting to Tenant ID 1
    private val defaultTenantId = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        paymentAdapter = PaymentAdapter { payment ->
            val pdfFile = PdfHelper.generateReceipt(requireContext(), payment)
            if (pdfFile != null) {
                sharePdfViaWhatsApp(pdfFile)
            } else {
                Toast.makeText(requireContext(), "Failed to generate receipt", Toast.LENGTH_SHORT).show()
            }
        }
        binding.rvPayments.adapter = paymentAdapter

        binding.fabAddPayment.setOnClickListener {
            showAddPaymentDialog()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.payments.collect { payments ->
                    paymentAdapter.submitList(payments)
                }
            }
        }
    }

    private fun showAddPaymentDialog() {
        val dialog = BottomSheetDialog(requireContext())
        val dialogBinding = DialogAddPaymentBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        val statuses = arrayOf("Paid", "Pending")
        dialogBinding.actvStatus.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, statuses))

        val methods = arrayOf("Cash", "Bank", "EasyPaisa", "JazzCash")
        dialogBinding.actvMethod.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, methods))

        dialogBinding.btnSave.setOnClickListener {
            val amountStr = dialogBinding.etAmount.text.toString().trim()
            val monthYear = dialogBinding.etMonthYear.text.toString().trim()
            val status = dialogBinding.actvStatus.text.toString().trim()
            val method = dialogBinding.actvMethod.text.toString().trim()
            val notes = dialogBinding.etNotes.text.toString().trim()

            if (amountStr.isEmpty() || monthYear.isEmpty() || status.isEmpty() || method.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.addPayment(
                tenantId = defaultTenantId,
                amount = amountStr.toDouble(),
                monthYear = monthYear,
                status = status,
                method = method,
                notes = notes
            )
            dialog.dismiss()
            Toast.makeText(requireContext(), "Payment Recorded", Toast.LENGTH_SHORT).show()
        }
        dialog.show()
    }

    private fun sharePdfViaWhatsApp(file: java.io.File) {
        val uri: Uri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            file
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_TEXT, "Here is your rent receipt.")
            setPackage("com.whatsapp")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "WhatsApp is not installed", Toast.LENGTH_SHORT).show()
            // Fallback to general sharing
            val fallbackIntent = Intent.createChooser(intent, "Share Receipt")
            startActivity(fallbackIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}