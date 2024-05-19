package com.littlelemon.bukharaadmin

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.littlelemon.bukharaadmin.databinding.ActivitySignupBinding
import com.littlelemon.bukharaadmin.model.UserModel

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var userName:String
    private lateinit var restaurantName:String
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var auth:FirebaseAuth
    private lateinit var dataBase: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        dataBase = Firebase.database.reference


        binding.btnSignSignup.setOnClickListener{
            userName =binding.signName.text.toString().trim()
            restaurantName =binding.etRestaurant.text.toString().trim()
            email =binding.signEmail.text.toString().trim()
            password =binding.signPassword.text.toString().trim()

            if (userName.isBlank() || restaurantName.isBlank() || email.isBlank() || password.isBlank() ){
                Toast.makeText(this,"Please fill all blanks" ,Toast.LENGTH_SHORT).show()
            }else{
                createAccount(email,password)
            }

        }

        binding.tvAlreadyhaveaccount.setOnClickListener {
            val intent = Intent (this,LoginActivity::class.java)
            startActivity(intent)
        }
        val locationList =arrayOf("District-1","District-2","District-3","District-4","District-5","District-6","District-7","District-8","District-9","District-10","District-11","District-12")
        val adapter = ArrayAdapter(this,R.layout.simple_dropdown_item_1line,locationList)
        val autoCompleteTextView = binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)



    }

    private fun createAccount(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{task ->
            if (task.isSuccessful){
                Toast.makeText(this,"Account created successfully" ,Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent = Intent (this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this,"Account Creation Failed" ,Toast.LENGTH_SHORT).show()
                Log.d("Account","createAccount: Failure",task.exception)
            }



        }
    }

    private fun saveUserData() {
        userName =binding.signName.text.toString().trim()
        restaurantName =binding.etRestaurant.text.toString().trim()
        email =binding.signEmail.text.toString().trim()
        password =binding.signPassword.text.toString().trim()
        val user= UserModel(userName,restaurantName,email,password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        dataBase.child("user").child(userId).setValue(user)
    }
}