package com.littlelemon.bukharaadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.littlelemon.bukharaadmin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
//    private lateinit var auth: FirebaseAuth
    private val binding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
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
//        binding.logout.setOnClickListener{
//auth.signOut()
//        }
    }
}