package com.littlelemon.bukharaadmin.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.littlelemon.bukharaadmin.databinding.AllitemBinding
import com.littlelemon.bukharaadmin.model.AllMenuModel

class AllItemAdapter(
private val context: Context,
    private val menuList :ArrayList<AllMenuModel>,
    databaseReference: DatabaseReference
) :
    RecyclerView.Adapter<AllItemAdapter.AllItemViewHolder>() {

    private val menuItemQuantity = IntArray(menuList.size){1}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllItemViewHolder {
       val binding = AllitemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
    return AllItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllItemViewHolder, position: Int) {
     holder.bind(position)
    }

    override fun getItemCount(): Int {
      return menuList.size
    }

 inner class AllItemViewHolder(private val binding: AllitemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
              binding.apply {
val quantity = menuItemQuantity[position]
                  val menuItem = menuList[position]
                  val uriString = menuItem.foodImage
                  val uri = Uri.parse(uriString)
                  tvFoodNameCart.text= menuItem.foodName
                  tvFoodPriceCart.text = menuItem.foodPrice
                  Glide.with(context).load(uri).into(ivCartImage)
                  tvCartQuantity.text=quantity.toString()

                  cartMinus.setOnClickListener{
                      decreaseQuantity(position)

                  }
                  cartPlus.setOnClickListener {
                      increaseQuantity(position)

                  }
                  cartDeleteIcon.setOnClickListener{
                   deleteItem(position)
                  }
              }
        }

     private  fun increaseQuantity(position: Int){
         if (menuItemQuantity[position]<10){
             menuItemQuantity[position]++
             binding.tvCartQuantity.text = menuItemQuantity[position].toString()
         }
     }
     private fun decreaseQuantity(position: Int){
         if (menuItemQuantity[position]>1){
             menuItemQuantity[position]--
             binding.tvCartQuantity.text = menuItemQuantity[position].toString()
         }
     }
     private fun deleteItem(position: Int){
         menuList.removeAt(position)
         menuList.removeAt(position)
         menuList.removeAt(position)
         notifyItemRemoved(position)
         notifyItemRangeChanged(position,menuList.size)

     }

    }
}