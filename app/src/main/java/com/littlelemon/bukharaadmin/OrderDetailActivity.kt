package com.littlelemon.bukharaadmin

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.littlelemon.bukharaadmin.Adapter.OrderDetailsAdapter
import com.littlelemon.bukharaadmin.databinding.ActivityOrderDetailBinding
import com.littlelemon.bukharaadmin.model.OrderDetails

class OrderDetailActivity : AppCompatActivity() {
    private val binding:ActivityOrderDetailBinding by lazy {
        ActivityOrderDetailBinding.inflate(layoutInflater)
    }

    private var userName:String ? = null
    private var address:String ? = null
    private var phoneNum:String ? = null
    private var totalPrice:String ? = null
    private var foodName:ArrayList<String> = arrayListOf()
    private var foodPrice:ArrayList<String> = arrayListOf()
    private var foodQuantity:ArrayList<Int> = arrayListOf()
    private var foodImage:ArrayList<String> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            finish()
        }

        getDataFromIntent()
    }

    private fun getDataFromIntent() {
        val receivedOrderDetail = intent.getSerializableExtra("UserOrderDetails") as OrderDetails
        receivedOrderDetail?.let {  OrderDetails ->

                userName = receivedOrderDetail.userName
                address = receivedOrderDetail.address
                phoneNum = receivedOrderDetail.phoneNum
                totalPrice = receivedOrderDetail.totalPrice
                foodName = receivedOrderDetail.foodName as ArrayList<String>
                foodPrice = receivedOrderDetail.foodPrice as ArrayList<String>
                foodQuantity= receivedOrderDetail.foodQuantity as ArrayList<Int>
                foodImage = receivedOrderDetail.foodImage as ArrayList<String>

                setUserDetails()
                setAdapter()

        }

    }


    private fun setUserDetails() {
        binding.name.text = userName
        binding.address.text =  address
        binding.phone.text = phoneNum
        binding.totalAmount.text = totalPrice
    }
    private fun setAdapter() {
     binding.orderDetailsRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = OrderDetailsAdapter(this,foodName,foodImage,foodPrice,foodQuantity)
        binding.orderDetailsRecyclerView.adapter = adapter
    }
}