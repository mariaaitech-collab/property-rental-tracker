package com.example.propertyrentaltracker.ui.payments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.propertyrentaltracker.data.db.entity.Payment
import com.example.propertyrentaltracker.databinding.ItemPaymentBinding

class PaymentAdapter(
    private val onReceiptClick: (Payment) -> Unit
) : ListAdapter<Payment, PaymentAdapter.PaymentViewHolder>(PaymentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val binding = ItemPaymentBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PaymentViewHolder(binding, onReceiptClick)
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PaymentViewHolder(
        private val binding: ItemPaymentBinding,
        private val onReceiptClick: (Payment) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(payment: Payment) {
            binding.tvAmount.text = "PKR ${payment.amountPaid}"
            binding.tvDateMonth.text = "Month: ${payment.monthYear}"
            binding.tvMethod.text = payment.paymentMethod
            binding.tvStatus.text = payment.paymentStatus

            // For actions menu (Receipts / WhatsApp)
            binding.ivActionMenu.setOnClickListener {
                onReceiptClick(payment)
            }
        }
    }

    class PaymentDiffCallback : DiffUtil.ItemCallback<Payment>() {
        override fun areItemsTheSame(oldItem: Payment, newItem: Payment): Boolean {
            return oldItem.paymentId == newItem.paymentId
        }

        override fun areContentsTheSame(oldItem: Payment, newItem: Payment): Boolean {
            return oldItem == newItem
        }
    }
}
