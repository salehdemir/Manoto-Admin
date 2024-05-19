package com.littlelemon.bukharaadmin.Adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.littlelemon.bukharaadmin.databinding.DeliveryitemBinding

class DeliveryAdapter(
    private val CustomerName: ArrayList<String>,
    private val PaymentStatus: ArrayList<String>,

) :
    RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryAdapter.DeliveryViewHolder {
        val binding = DeliveryitemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return DeliveryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeliveryAdapter.DeliveryViewHolder, position: Int) {
holder.bind(position)
    }

    override fun getItemCount(): Int {
        return CustomerName.size
    }

    inner class DeliveryViewHolder(private val binding: DeliveryitemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                tvCustomerName.text = CustomerName[position]
                tvReceived.text = PaymentStatus[position]
                val colorMap = mapOf(
                    "Received" to Color.GREEN, "Not Received" to Color.RED, "Pending" to Color.GRAY
                )
                tvReceived.setTextColor(colorMap[PaymentStatus[position]]?:Color.BLACK)
                deliveryColor.backgroundTintList = ColorStateList.valueOf(colorMap[PaymentStatus[position]]?:Color.BLACK)
            }
        }

    }

    }