package com.littlelemon.bukharaadmin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.littlelemon.bukharaadmin.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var email:String
    private lateinit var password:String
    private lateinit var auth: FirebaseAuth
    private lateinit var dataBase: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient


    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        auth = Firebase.auth
        dataBase = Firebase.database.reference
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions)


        binding.btnLogLogin.setOnClickListener{
            email =binding.logEmail.text.toString().trim()
            password =binding.logPassword.text.toString().trim()

            if (  email.isBlank() || password.isBlank() ){
                Toast.makeText(this,"Please fill all blanks" , Toast.LENGTH_SHORT).show()
            }else{
                createAccount(email,password)
            }

//            val intent = Intent(this,MainActivity::class.java)
//            startActivity(intent)
        }
        binding.btnLogGoogle.setOnClickListener{
            val intent = googleSignInClient.signInIntent
            launcher.launch(intent)
        }

        binding.tvDontHaveAccount.setOnClickListener {
            val intent = Intent (this,SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{task ->
            if (task.isSuccessful){
                val user = auth.currentUser
                Toast.makeText(this,"Login Successfully" ,Toast.LENGTH_SHORT).show()

                updateUi(user)
            }else{
                Toast.makeText(this,"Login failed" ,Toast.LENGTH_SHORT).show()
                Log.d("Account","Login: Failure",task.exception)
            }

        }
    }


    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if(task.isSuccessful){
                val account : GoogleSignInAccount = task.result
                val credential =GoogleAuthProvider.getCredential(account.idToken,null)
                auth.signInWithCredential(credential).addOnCompleteListener { authTask  ->
                    if (authTask.isSuccessful){
                        Toast.makeText(this,"successfully sign in with google" , Toast.LENGTH_SHORT).show()
                       updateUi(authTask.result?.user)
                        finish()
                    }else{
                        Toast.makeText(this,"sign in failed" , Toast.LENGTH_SHORT).show()

                    }
                }
            }else{
                Toast.makeText(this,"sign in failed" , Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser  = auth.currentUser
        if (currentUser != null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }

    private fun updateUi(user: FirebaseUser?) {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}

