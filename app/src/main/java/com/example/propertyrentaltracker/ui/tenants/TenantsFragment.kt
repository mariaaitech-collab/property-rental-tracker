package com.example.propertyrentaltracker.ui.tenants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.propertyrentaltracker.databinding.DialogAddTenantBinding
import com.example.propertyrentaltracker.databinding.FragmentTenantsBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TenantsFragment : Fragment() {

    private var _binding: FragmentTenantsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TenantViewModel by viewModels()
    private lateinit var tenantAdapter: TenantAdapter

    // TODO: For a complete app, we'd select a property first. For now, defaulting to Property ID 1
    private val defaultPropertyId = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTenantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tenantAdapter = TenantAdapter { tenant ->
            Toast.makeText(requireContext(), "Clicked: ${tenant.tenantName}", Toast.LENGTH_SHORT).show()
        }
        binding.rvTenants.adapter = tenantAdapter

        binding.fabAddTenant.setOnClickListener {
            showAddTenantDialog()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.tenants.collect { tenants ->
                    tenantAdapter.submitList(tenants)
                    // You'd need to add tvEmpty to fragment_tenants.xml similarly to properties
                }
            }
        }
    }

    private fun showAddTenantDialog() {
        val dialog = BottomSheetDialog(requireContext())
        val dialogBinding = DialogAddTenantBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.btnSave.setOnClickListener {
            val name = dialogBinding.etTenantName.text.toString().trim()
            val phone = dialogBinding.etPhone.text.toString().trim()
            val cnic = dialogBinding.etCnic.text.toString().trim()
            val unit = dialogBinding.etUnit.text.toString().trim()
            val rentStr = dialogBinding.etRent.text.toString().trim()
            val dueDayStr = dialogBinding.etDueDay.text.toString().trim()

            if (name.isEmpty() || phone.isEmpty() || rentStr.isEmpty() || dueDayStr.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.addTenant(
                propertyId = defaultPropertyId,
                name = name,
                phone = phone,
                cnic = cnic,
                unit = unit,
                rent = rentStr.toDouble(),
                dueDay = dueDayStr.toInt()
            )
            dialog.dismiss()
            Toast.makeText(requireContext(), "Tenant added", Toast.LENGTH_SHORT).show()
        }
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}