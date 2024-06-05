package com.littlelemon.bukharaadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.littlelemon.bukharaadmin.Adapter.DeliveryAdapter
import com.littlelemon.bukharaadmin.databinding.ActivityOutFoodDeliveryBinding
import com.littlelemon.bukharaadmin.model.OrderDetails

class OutFoodDeliveryActivity : AppCompatActivity() {
    private val binding: ActivityOutFoodDeliveryBinding by lazy {
        ActivityOutFoodDeliveryBinding.inflate(layoutInflater)
    }
    private lateinit var database: FirebaseDatabase
    private var listOfCompleteOrder :ArrayList<OrderDetails> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonBack.setOnClickListener {
            finish()
        }
        retrieveCompleteOrderDetail()


    }

    private fun retrieveCompleteOrderDetail() {
//        Initialize firebase database
        database = FirebaseDatabase.getInstance()
        val completeOrderRef = database.reference.child("CompletedOrder").orderByChild("currentTime")
        completeOrderRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
//                clear the list before populating to new data
                listOfCompleteOrder.clear()
                for (orderSnapShot in snapshot.children){
                    val completeOrder = orderSnapShot.getValue(OrderDetails::class.java)
                    completeOrder?.let {
                        listOfCompleteOrder.add(it)
                    }
                }
//                reverse the list to display the latest data
                listOfCompleteOrder.reverse()
                setDataIntoRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun setDataIntoRecyclerView() {
//        Initialization list to hold customer name and payment status
        val customerName = mutableListOf<String>()
        val paymentStatus = mutableListOf<Boolean>()

        for (order in listOfCompleteOrder){
            order.userName?.let {
                customerName.add(it)

            }
            paymentStatus.add(order.paymentReceived)
        }
        val adapter = DeliveryAdapter(customerName,paymentStatus)
        binding.deliveryRecycler.adapter = adapter
        binding.deliveryRecycler.layoutManager = LinearLayoutManager(this)

    }
}