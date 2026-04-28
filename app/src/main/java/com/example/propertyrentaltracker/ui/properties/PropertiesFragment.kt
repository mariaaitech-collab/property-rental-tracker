package com.example.propertyrentaltracker.ui.properties

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.propertyrentaltracker.R
import com.example.propertyrentaltracker.databinding.DialogAddPropertyBinding
import com.example.propertyrentaltracker.databinding.FragmentPropertiesBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PropertiesFragment : Fragment() {

    private var _binding: FragmentPropertiesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PropertyViewModel by viewModels()
    private lateinit var propertyAdapter: PropertyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPropertiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        propertyAdapter = PropertyAdapter { property ->
            Toast.makeText(requireContext(), "Clicked: ${property.propertyName}", Toast.LENGTH_SHORT).show()
        }
        binding.rvProperties.adapter = propertyAdapter

        binding.fabAddProperty.setOnClickListener {
            showAddPropertyDialog()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.properties.collect { properties ->
                    propertyAdapter.submitList(properties)
                    if (properties.isEmpty()) {
                        binding.tvEmpty.visibility = View.VISIBLE
                    } else {
                        binding.tvEmpty.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun showAddPropertyDialog() {
        val dialog = BottomSheetDialog(requireContext())
        val dialogBinding = DialogAddPropertyBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        val types = arrayOf("Residential", "Commercial", "Mixed")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, types)
        dialogBinding.actvType.setAdapter(adapter)

        dialogBinding.btnSave.setOnClickListener {
            val name = dialogBinding.etPropertyName.text.toString().trim()
            val address = dialogBinding.etAddress.text.toString().trim()
            val unitsStr = dialogBinding.etTotalUnits.text.toString().trim()
            val type = dialogBinding.actvType.text.toString().trim()

            if (name.isEmpty() || address.isEmpty() || unitsStr.isEmpty() || type.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.addProperty(name, address, unitsStr.toInt(), type)
            dialog.dismiss()
            Toast.makeText(requireContext(), "Property added", Toast.LENGTH_SHORT).show()
        }
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}