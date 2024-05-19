package com.littlelemon.bukharaadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.littlelemon.bukharaadmin.Adapter.PendingAdapter
import com.littlelemon.bukharaadmin.databinding.ActivityPendingBinding
import com.littlelemon.bukharaadmin.model.OrderDetails

class PendingActivity : AppCompatActivity(),PendingAdapter.OnItemClicked {
    private lateinit var binding: ActivityPendingBinding

    private var listOfName: MutableList<String> = mutableListOf()
//    private var listOfTotalPrice: MutableList<String> = mutableListOf()
    private var listOfFoodPrice :MutableList<String> = mutableListOf()
    private var listOfImageFirstOrder: MutableList<String> = mutableListOf()
    private var listOfOrder: ArrayList<OrderDetails> = arrayListOf()
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseOrderDetails: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        databaseOrderDetails = database.reference.child("OrderDetails")

        getOrderDetails()

        binding.buttonBack.setOnClickListener {
            finish()
        }
    }

    private fun getOrderDetails() {
//         get order details from firebase database
        databaseOrderDetails.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (orderSnapshot in snapshot.children) {
                    val orderDetails = orderSnapshot.getValue(OrderDetails::class.java)
                    orderDetails?.let {
                        listOfOrder.add(it)
                    }
                }
                addDataToListForRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun addDataToListForRecyclerView() {
        for (orderItem in listOfOrder){
//            add data to list for populating the recycler view
            orderItem.userName?.let { listOfName.add(it) }
//            orderItem.totalPrice?.let { listOfTotalPrice.add(it) }
            orderItem.foodPrice?.let { listOfFoodPrice.add(it.toString()) }
            orderItem.foodImage?.filterNot { it.isEmpty() }?.forEach{
                listOfImageFirstOrder.add(it)
            }
        }
        // Log the sizes of the lists
        println("listOfName size: ${listOfName.size}")
//        println("listOfTotalPrice size: ${listOfTotalPrice.size}")
        println("listOfTotalPrice size: ${listOfFoodPrice.size}")

        println("listOfImageFirstOrder size: ${listOfImageFirstOrder.size}")

        // Check if lists are not empty before setting the adapter
        if (listOfName.isNotEmpty()
//            &&
//            listOfTotalPrice.isNotEmpty()
            &&listOfFoodPrice.isNotEmpty()
            && listOfImageFirstOrder.isNotEmpty()) {
            setAdapter()
        } else {
            // Handle the case where lists are empty
            println("One or more lists are empty, adapter will not be set")
        }
    }

    private fun setAdapter() {
        binding.pendingRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = PendingAdapter(this,listOfName,
//            listOfTotalPrice,
            listOfFoodPrice,listOfImageFirstOrder,this)
        binding.pendingRecyclerView.adapter = adapter
    }

    override fun onItemClickListener(position: Int) {
          val intent = Intent(this,OrderDetailActivity::class.java)
        val userOrderDetails = listOfOrder[position]
        intent.putExtra("UserOrderDetails",userOrderDetails)
        startActivity(intent)
    }
}