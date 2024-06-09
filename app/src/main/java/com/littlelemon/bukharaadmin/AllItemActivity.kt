package com.littlelemon.bukharaadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.littlelemon.bukharaadmin.Adapter.AllItemAdapter
import com.littlelemon.bukharaadmin.databinding.ActivityAllItemBinding
import com.littlelemon.bukharaadmin.model.AllMenuModel

class AllItemActivity : AppCompatActivity() {
    private val binding:ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private  var menuItems:ArrayList<AllMenuModel> =ArrayList()
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

            databaseReference= FirebaseDatabase.getInstance().reference
        retrieveMenuItem()

                binding.buttonBack.setOnClickListener {
            finish()
        }


    }

    private fun retrieveMenuItem() {
database =FirebaseDatabase.getInstance()
        val foodRef:DatabaseReference =database.reference.child("menu")

//        Fetch data from dataBase
        foodRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
//             clear existing data before populating
                menuItems.clear()

                for (foodSnapshot in snapshot.children){
                    val menuItem = foodSnapshot.getValue(AllMenuModel::class.java)
                    menuItem?.let {
                        menuItems.add(it)
                    }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError","Error: ${error.message}")
            }
        })
    }

    private fun setAdapter() {

        val adapter = AllItemAdapter(
          this@AllItemActivity,menuItems,databaseReference
        ){position ->
            deleteMenuItem(position)

        }

        binding.allItemRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.allItemRecyclerView.adapter = adapter
    }

    private fun deleteMenuItem(position: Int) {
val menuItemToDelete = menuItems[position]
        val menuItemKey = menuItemToDelete.key
        val foodMenuRef = database.reference.child("menu").child(menuItemKey!!)
        foodMenuRef.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful){
                menuItems.removeAt(position)
                binding.allItemRecyclerView.adapter?.notifyItemRemoved(position)

            }else{
                Toast.makeText(this,"Item not deleted",Toast.LENGTH_SHORT).show()
            }
        }
    }
}