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
        setTheme(R.style.Theme_InstaSim)
        setContentView(R.layout.activity_login)

        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            goPostActivity()
        }

        btnLogin.setOnClickListener {

            btnLogin.isEnabled = false
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "email/password cannot be empty", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                btnLogin.isEnabled = true
                if (task.isSuccessful) {
                    Toast.makeText(this, "Success!!", Toast.LENGTH_SHORT).show()
                    goPostActivity()
                } else {
                    Log.e(TAG, "signInWithEmailFailed", task.exception)
                    Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }


    }

    private fun goPostActivity() {
        Log.i(TAG, "goPostActivity")
        val intent = Intent(this, PostActivity::class.java)
        startActivity(intent)
        finish()
    }
}