package com.littlelemon.bukharaadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.littlelemon.bukharaadmin.databinding.ActivityAdminProfileBinding
import com.littlelemon.bukharaadmin.model.UserModel

class AdminProfileActivity : AppCompatActivity() {
    private val binding : ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    private lateinit var auth:FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var adminReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        adminReference = database.reference.child("user")

        binding.buttonBack.setOnClickListener {
            finish()
        }
        binding.btnSaveInfo.setOnClickListener {
            updateUserData()
        }

        binding.etName.isEnabled = false
        binding.etAddress.isEnabled = false
        binding.etEmail.isEnabled = false
        binding.phone.isEnabled = false
        binding.etPassword.isEnabled = false
        binding.btnSaveInfo.isEnabled = false

        var isEnabled = false
        binding.editProfile.setOnClickListener {
            isEnabled = ! isEnabled
            binding.etName.isEnabled = isEnabled
            binding.etAddress.isEnabled = isEnabled
            binding.etEmail.isEnabled = isEnabled
            binding.phone.isEnabled = isEnabled
            binding.etPassword.isEnabled = isEnabled
            if(isEnabled){
                binding.etName.requestFocus()
            }
            binding.btnSaveInfo.isEnabled = isEnabled

        }
        retrieveUserData()
    }



    private fun retrieveUserData() {
        val currentUserUid = auth.currentUser?.uid
        if (currentUserUid != null){
            val userReference = adminReference.child(currentUserUid)
            userReference.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        var ownerName = snapshot.child("name").getValue()
                        var address = snapshot.child("address").getValue()
                        var email = snapshot.child("email").getValue()
                        var phone = snapshot.child("phone").getValue()
                        var password = snapshot.child("password").getValue()
                        setDataToTextView(ownerName,address,email,phone,password)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        }

    }

    private fun setDataToTextView(
        ownerName: Any?,
        address: Any?,
        email: Any?,
        phone: Any?,
        password: Any?
    ) {

        binding.etName.setText(ownerName.toString())
        binding.etAddress.setText(address.toString())
        binding.etEmail.setText(email.toString())
        binding.phone.setText(phone.toString())
        binding.etPassword.setText(password.toString())
    }

    private fun updateUserData() {
val updateName =binding.etName.text.toString()
val updateAddress =binding.etAddress.text.toString()
val updateEmail =binding.etEmail.text.toString()
val updatePhone =binding.phone.text.toString()
val updatePassword =binding.etPassword.text.toString()
        val currentUserUid = auth.currentUser?.uid
        if (currentUserUid != null){

            val userReference = adminReference.child(currentUserUid)
            userReference.child("name").setValue(updateName)
            userReference.child("address").setValue(updateAddress)
            userReference.child("email").setValue(updateEmail)
            userReference.child("phone").setValue(updatePhone)
            userReference.child("password").setValue(updatePassword)

            Toast.makeText(this,"Profile Update Successfully ",Toast.LENGTH_SHORT).show()
            auth.currentUser?.updateEmail(updateEmail)
            auth.currentUser?.updatePassword(updatePassword)

        }



        else{
            Toast.makeText(this,"Profile Update Failed ",Toast.LENGTH_SHORT).show()

        }
    }
}