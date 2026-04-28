package com.example.propertyrentaltracker.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.propertyrentaltracker.R
import com.example.propertyrentaltracker.data.db.AppDatabase
import com.example.propertyrentaltracker.databinding.FragmentDashboardBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    // View Binding
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    // ViewModel
    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate view binding
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup UI components
        setupUI()

        // Observe data from ViewModel
        observeData()

        // Setup income chart
        setupIncomeChart()
    }

    private fun setupUI() {
        // Setup greeting based on time of day
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)

        val greeting = when (hour) {
            in 0..11 -> "Good Morning ☀️"
            in 12..17 -> "Good Afternoon 🌤️"
            else -> "Good Evening 🌙"
        }
        binding.tvGreeting.text = greeting

        // Set current date
        val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
        binding.tvCurrentDate.text = dateFormat.format(Date())

        // Setup click listeners for navigation cards
        binding.cardProperties.setOnClickListener {
            val bottomNav = requireActivity().findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.nav_view)
            bottomNav?.selectedItemId = R.id.nav_properties
        }

        binding.cardTenants.setOnClickListener {
            val bottomNav = requireActivity().findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.nav_view)
            bottomNav?.selectedItemId = R.id.nav_tenants
        }

        binding.cardPayments.setOnClickListener {
            val bottomNav = requireActivity().findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.nav_view)
            bottomNav?.selectedItemId = R.id.nav_payments
        }

        binding.cardMaintenance.setOnClickListener {
            val bottomNav = requireActivity().findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.nav_view)
            bottomNav?.selectedItemId = R.id.nav_more
        }
    }

    private fun observeData() {
        // Observe total properties count
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.totalProperties.observe(viewLifecycleOwner) { count ->
                binding.tvTotalProperties.text = count.toString()
            }
        }

        // Observe total tenants count
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.totalTenants.observe(viewLifecycleOwner) { count ->
                binding.tvTotalTenants.text = count.toString()
            }
        }

        // Observe total income
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.totalIncome.observe(viewLifecycleOwner) { income ->
                val formattedIncome = String.format("%,.0f", income ?: 0.0)
                binding.tvTotalIncome.text = "PKR $formattedIncome"
            }
        }

        // Observe pending payments
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pendingPayments.observe(viewLifecycleOwner) { pending ->
                val pendingCount = pending?.toInt() ?: 0
                binding.tvPendingPayments.text = pendingCount.toString()

                // Change color to red if pending > 0
                if (pendingCount > 0) {
                    binding.tvPendingPayments.setTextColor(
                        resources.getColor(R.color.accent_error, null)
                    )
                } else {
                    binding.tvPendingPayments.setTextColor(
                        resources.getColor(R.color.accent_warning, null)
                    )
                }
            }
        }
    }

    private fun setupIncomeChart() {
        // Sample data for monthly income chart
        // In real app, this would come from database
        val entries = listOf(
            BarEntry(0f, 45000f),  // Jan
            BarEntry(1f, 52000f),  // Feb
            BarEntry(2f, 48000f),  // Mar
            BarEntry(3f, 61000f),  // Apr
            BarEntry(4f, 55000f),  // May
            BarEntry(5f, 67000f)   // Jun
        )

        // Create dataset with styling
        val dataSet = BarDataSet(entries, "Monthly Income (PKR)").apply {
            color = resources.getColor(R.color.primary, null)      // Blue bars
            valueTextColor = resources.getColor(R.color.text_primary, null)
            valueTextSize = 12f
            setDrawValues(true)  // Show values on top of bars
        }

        // Create bar data object
        val barData = BarData(dataSet)
        barData.barWidth = 0.6f  // Width of bars

        // Configure and apply chart settings
        binding.chartIncome.apply {
            data = barData

            // Disable description text
            description.isEnabled = false

            // Enable touch interactions
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)

            // Configure X Axis (bottom axis - months)
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM  // Show at bottom
                setDrawGridLines(false)                 // Hide grid lines
                granularity = 1f                        // Interval of 1
                textColor = resources.getColor(R.color.text_secondary, null)

                // Custom month labels
                valueFormatter = IndexAxisValueFormatter(
                    arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun")
                )
            }

            // Configure Left Y Axis (income values)
            axisLeft.apply {
                setDrawGridLines(true)                  // Show grid lines
                gridColor = resources.getColor(R.color.background_primary, null)
                axisMinimum = 0f                        // Start from 0
                textColor = resources.getColor(R.color.text_secondary, null)
                setDrawAxisLine(true)
            }

            // Disable Right Y Axis
            axisRight.isEnabled = false

            // Add animation
            animateY(1000)  // Animate in 1 second

            // Refresh chart
            invalidate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Avoid memory leaks by clearing binding reference
        _binding = null
    }

    companion object {
        // Factory method for creating instances if needed
        @JvmStatic
        fun newInstance() = DashboardFragment()
    }
}