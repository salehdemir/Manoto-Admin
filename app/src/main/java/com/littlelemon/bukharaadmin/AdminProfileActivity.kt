package com.littlelemon.bukharaadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.littlelemon.bukharaadmin.databinding.ActivityAdminProfileBinding

class AdminProfileActivity : AppCompatActivity() {
    private val binding : ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonBack.setOnClickListener {
            finish()
        }

        binding.etName.isEnabled = false
        binding.etAddress.isEnabled = false
        binding.etEmail.isEnabled = false
        binding.phone.isEnabled = false
        binding.etPassword.isEnabled = false

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

        }
    }
}