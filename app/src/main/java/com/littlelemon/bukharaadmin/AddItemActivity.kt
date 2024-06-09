package com.littlelemon.bukharaadmin

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.littlelemon.bukharaadmin.databinding.ActivityAddItemBinding
import com.littlelemon.bukharaadmin.model.AllMenuModel

class AddItemActivity : AppCompatActivity() {
    private val binding:ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }
    

    private lateinit var foodName:String
    private lateinit var foodPrice:String
    private lateinit var foodDescription:String
    private lateinit var foodIngredient:String
    private var foodImageUri: Uri?  = null

    private lateinit var auth:FirebaseAuth
    private lateinit var database :FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.btnAddItem.setOnClickListener {
            foodName = binding.etName.text.toString().trim()
            foodPrice = binding.etPrice.text.toString().trim()
            foodDescription = binding.tvDetail.text.toString().trim()
            foodIngredient = binding.tvingredientsDetails.text.toString().trim()
            if (!(foodName.isBlank()||foodPrice.isBlank()||foodDescription.isBlank()||foodIngredient.isBlank())){
                uploadData()
                Toast.makeText(this,"Item Added Successfully", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this,"Item doesn't add / fill all the blanks", Toast.LENGTH_SHORT).show()

            }

        }
        binding.addImage.setOnClickListener{
            pickImage.launch("image/*")
        }



        binding.buttonBack.setOnClickListener{
            finish()
        }
    }

    private fun uploadData() {

        val menuReference = database.getReference("menu")
        val newItemKey = menuReference.push().key

        if (foodImageUri != null){
            val storageReference = FirebaseStorage.getInstance().reference
            val imageRef = storageReference.child("menuImage/${newItemKey}.jpg")
            val uploadTask = imageRef.putFile(foodImageUri!!)

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->

                    val newItem = AllMenuModel(
                        newItemKey,
                        foodName = foodName ,
                        foodPrice = foodPrice,
                        foodImage = downloadUrl.toString(),
                        foodDescription = foodDescription,
                        foodIngredient =foodIngredient

                    )
                    newItemKey?.let { key ->
                        menuReference.child(key).setValue(newItem).addOnSuccessListener {
                            Toast.makeText(this,"Data uploaded successfully", Toast.LENGTH_SHORT).show()
                        }
                            .addOnFailureListener{
                            Toast.makeText(this,"Data uploaded failed", Toast.LENGTH_SHORT).show()

                        }
                    }
                }
            }
                .addOnFailureListener{
                Toast.makeText(this,"Image uploaded failed", Toast.LENGTH_SHORT).show()

            }

        }
        else{
            Toast.makeText(this,"Please select an image", Toast.LENGTH_SHORT).show()

        }

    }

   private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) {uri ->
        if (uri != null){
            binding.selectedImage.setImageURI(uri)
            foodImageUri = uri
        }
    }
}