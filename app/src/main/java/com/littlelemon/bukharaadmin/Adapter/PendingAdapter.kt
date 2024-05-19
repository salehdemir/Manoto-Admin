package com.littlelemon.bukharaadmin.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.littlelemon.bukharaadmin.databinding.PendingorderBinding

 class PendingAdapter(
private val context: Context,
     private val customerName: MutableList<String>,
     private val quantity: MutableList<String>,
     private val foodImage: MutableList<String>,
     private val itemClicked :OnItemClicked

) :
    RecyclerView.Adapter<PendingAdapter.PendingViewHolder>() {

        interface OnItemClicked{
            fun onItemClickListener(position: Int)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingViewHolder {
        val binding = PendingorderBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return PendingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PendingViewHolder, position: Int) {
       holder.bind(position)
    }

    override fun getItemCount(): Int = customerName.size

    inner class PendingViewHolder (private val binding: PendingorderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var isAccepted = false

        fun bind(position: Int) {


          binding.apply {
              pendingName.text = customerName[position]
               pendingQuantityPrice.text = quantity[position]
              val uriString =foodImage[position]
              val uri = Uri.parse(uriString)
              Glide.with(context).load(uri).into(pendingImage)
              btnAccepted.apply {


              if (!isAccepted){
                  text = "Accept"
              }else{
                  text = "Dispatch"
              }

                  setOnClickListener {
                      if (!isAccepted){
                          text = "Dispatch"
                          isAccepted = true
                          showToast("Order is Accepted")
                      }else{
                         customerName.removeAt(adapterPosition)
                          notifyItemRemoved(adapterPosition)
                          showToast("Order is Dispatch")
                      }
                  }
              }
              itemView.setOnClickListener {
                  itemClicked.onItemClickListener(position)
              }
          }
        }

      private  fun showToast(message:String){
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }

    }

}