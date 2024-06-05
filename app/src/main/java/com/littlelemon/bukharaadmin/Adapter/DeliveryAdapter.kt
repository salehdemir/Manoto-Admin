package com.littlelemon.bukharaadmin.Adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.littlelemon.bukharaadmin.databinding.DeliveryitemBinding

class DeliveryAdapter(
    private val customerName: MutableList<String>,
    private val paymentStatus: MutableList<Boolean>
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
        return customerName.size
    }

    inner class DeliveryViewHolder(private val binding: DeliveryitemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                tvCustomerName.text = customerName[position]
                if (paymentStatus[position] == true ){
                    tvReceived.text = "Received"

                }else{
                    tvReceived.text = "Not Received"
                }

                val colorMap = mapOf(
                   true to Color.GREEN, false to Color.RED
                )
                tvReceived.setTextColor(colorMap[paymentStatus[position]]?:Color.BLACK)
                deliveryColor.backgroundTintList = ColorStateList.valueOf(colorMap[paymentStatus[position]]?:Color.BLACK)
            }
        }

    }

    }