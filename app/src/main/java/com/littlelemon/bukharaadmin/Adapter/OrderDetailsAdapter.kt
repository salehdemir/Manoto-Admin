package com.littlelemon.bukharaadmin.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.littlelemon.bukharaadmin.databinding.OrderdetailsitemsBinding

class OrderDetailsAdapter(
    private val context: Context,
    private var foodName :ArrayList<String>,
    private val foodImage :ArrayList<String>,
    private val foodPrice :ArrayList<String>,
    private val foodQuantity :ArrayList<Int>
):RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsViewHolder {
val binding = OrderdetailsitemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderDetailsViewHolder(binding)
    }


    override fun onBindViewHolder(holder: OrderDetailsViewHolder, position: Int) {
holder.bind(position)
    }
    override fun getItemCount(): Int = foodName.size
   inner class OrderDetailsViewHolder(private val binding: OrderdetailsitemsBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                tvFoodName.text = foodName[position]
                val uriString = foodImage[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(ivFoodImage)
                tvFoodPrice.text = foodPrice[position]
                tvFoodQuantity.text = foodQuantity[position].toString()
            }
        }

    }
}