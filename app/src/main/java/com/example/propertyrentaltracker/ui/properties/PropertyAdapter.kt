package com.example.propertyrentaltracker.ui.properties

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.propertyrentaltracker.data.db.entity.Property
import com.example.propertyrentaltracker.databinding.ItemPropertyBinding

class PropertyAdapter(private val onItemClick: (Property) -> Unit) :
    ListAdapter<Property, PropertyAdapter.PropertyViewHolder>(PropertyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val binding = ItemPropertyBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PropertyViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PropertyViewHolder(
        private val binding: ItemPropertyBinding,
        private val onItemClick: (Property) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(property: Property) {
            binding.tvPropertyName.text = property.propertyName
            binding.tvPropertyAddress.text = property.address
            binding.tvTotalUnits.text = "${property.totalUnits} Units"
            binding.tvPropertyType.text = property.propertyType

            binding.root.setOnClickListener {
                onItemClick(property)
            }
        }
    }

    class PropertyDiffCallback : DiffUtil.ItemCallback<Property>() {
        override fun areItemsTheSame(oldItem: Property, newItem: Property): Boolean {
            return oldItem.propertyId == newItem.propertyId
        }

        override fun areContentsTheSame(oldItem: Property, newItem: Property): Boolean {
            return oldItem == newItem
        }
    }
}
