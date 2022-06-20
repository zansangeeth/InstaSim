package com.zasa.instasim

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

private const val TAG = "LoginActivity"

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {

            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "email/password cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //Firebase authentication checking
            val auth = FirebaseAuth.getInstance()
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    task -> if (task.isSuccessful){
                Toast.makeText(this, "Success!!", Toast.LENGTH_SHORT).show()
                goPostActivity()
            }else{
                Log.e(TAG, "signInWithEmailFailed", task.exception)
                Toast.makeText(this,"Authentication Failed", Toast.LENGTH_SHORT).show()
            }

            }
        }


    }

    private fun goPostActivity() {
        Log.i(TAG, "goPostActivity")
        val intent = Intent(this, PostActivity::class.java)
        startActivity(intent)
    }
}