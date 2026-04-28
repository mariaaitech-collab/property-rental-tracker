package com.example.propertyrentaltracker.ui.tenants

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.propertyrentaltracker.data.db.entity.Tenant
import com.example.propertyrentaltracker.databinding.ItemTenantBinding

class TenantAdapter(private val onItemClick: (Tenant) -> Unit) :
    ListAdapter<Tenant, TenantAdapter.TenantViewHolder>(TenantDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenantViewHolder {
        val binding = ItemTenantBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TenantViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: TenantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TenantViewHolder(
        private val binding: ItemTenantBinding,
        private val onItemClick: (Tenant) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(tenant: Tenant) {
            binding.tvTenantName.text = tenant.tenantName
            binding.tvTenantPhone.text = tenant.phoneNumber
            binding.tvRentAmount.text = "PKR ${tenant.rentAmount}"
            binding.tvUnitInfo.text = "Unit: ${tenant.unitNumber}"

            binding.root.setOnClickListener {
                onItemClick(tenant)
            }
        }
    }

    class TenantDiffCallback : DiffUtil.ItemCallback<Tenant>() {
        override fun areItemsTheSame(oldItem: Tenant, newItem: Tenant): Boolean {
            return oldItem.tenantId == newItem.tenantId
        }

        override fun areContentsTheSame(oldItem: Tenant, newItem: Tenant): Boolean {
            return oldItem == newItem
        }
    }
}
