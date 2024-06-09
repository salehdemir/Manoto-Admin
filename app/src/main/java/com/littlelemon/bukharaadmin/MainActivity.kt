package com.littlelemon.bukharaadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.littlelemon.bukharaadmin.databinding.ActivityMainBinding
import com.littlelemon.bukharaadmin.model.OrderDetails

class MainActivity : AppCompatActivity() {
    private val binding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var completeOrderRef:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.addMenu.setOnClickListener{
            val intent = Intent(this,AddItemActivity::class.java)
            startActivity(intent)
        }
        binding.allItem.setOnClickListener{
            val intent = Intent(this,AllItemActivity::class.java)
            startActivity(intent)
        }
        binding.orderDispatch.setOnClickListener{
            val intent = Intent(this,OutFoodDeliveryActivity::class.java)
            startActivity(intent)
        }
        binding.profile.setOnClickListener{
            val intent = Intent(this,AdminProfileActivity::class.java)
            startActivity(intent)
        }

        binding.createUser.setOnClickListener{
            val intent = Intent(this,CreateUserActivity::class.java)
            startActivity(intent)
        }
        binding.tvPending.setOnClickListener{
            val intent = Intent(this,PendingActivity::class.java)
            startActivity(intent)
        }
        binding.logout.setOnClickListener{
            auth.signOut()
            startActivity(Intent(this,SignupActivity::class.java))
            finish()
        }



//        binding.logout.setOnClickListener{
//auth.signOut()
//        }

        pendingOrders()
        completedOrder()
        wholeTimeEarning()
    }

    private fun wholeTimeEarning() {
        val listOfTotalPay = mutableListOf<Int>()
        completeOrderRef =FirebaseDatabase.getInstance().reference.child("CompletedOrder")
        completeOrderRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
              for (orderSnapShot in snapshot.children){
                  val completedOrder = orderSnapShot.getValue(OrderDetails::class.java)
                  completedOrder?.totalPrice?.replace("$","")?.toIntOrNull()
                      ?.let { i ->
                          listOfTotalPay.add(i)
                      }
              }
                binding.wholeEarning.text =listOfTotalPay.sum().toString() + "$"
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun completedOrder() {
        val completedOrderRef= database.reference.child("CompletedOrder")
        var completedOrderItemCount = 0
        completedOrderRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                completedOrderItemCount = snapshot.childrenCount.toInt()
                binding.completedOrder.text =completedOrderItemCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun pendingOrders() {
database = FirebaseDatabase.getInstance()
        val pendingOrderRef= database.reference.child("OrderDetails")
        var pendingOrderItemCount = 0
        pendingOrderRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                pendingOrderItemCount = snapshot.childrenCount.toInt()
                binding.pendingOrder.text =pendingOrderItemCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}