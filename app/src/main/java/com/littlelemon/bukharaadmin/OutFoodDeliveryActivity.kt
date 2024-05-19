package com.littlelemon.bukharaadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.littlelemon.bukharaadmin.Adapter.DeliveryAdapter
import com.littlelemon.bukharaadmin.databinding.ActivityOutFoodDeliveryBinding

class OutFoodDeliveryActivity : AppCompatActivity() {
    private val binding: ActivityOutFoodDeliveryBinding by lazy {
        ActivityOutFoodDeliveryBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonBack.setOnClickListener {
            finish()
        }

        val customerName = arrayListOf("Saleh","Shadab ","Edward","Bashir","Raziq","Ghafoor")
        val paymentStatus = arrayListOf("Received","Pending","Received","Not Received","Pending","Not Received")

        val adapter = DeliveryAdapter(
            ArrayList ( customerName),
            ArrayList (  paymentStatus)

        )

        binding.deliveryRecycler.layoutManager = LinearLayoutManager(this)
        binding.deliveryRecycler.adapter = adapter
    }
}