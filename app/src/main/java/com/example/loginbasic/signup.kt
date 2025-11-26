package com.example.loginbasic

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class signup : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var editUsername: EditText
    private lateinit var editPassword: EditText
    private lateinit var btnSignup: Button
    private lateinit var tvLogin: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        dbHelper=DatabaseHelper(this)

        editUsername=findViewById(R.id.editusername)
        editPassword=findViewById(R.id.editpassword)
        btnSignup=findViewById(R.id.btnSignup)
        tvLogin=findViewById<TextView>(R.id.tvLogin)
        btnSignup.setOnClickListener {
            val username = editUsername.text.toString().trim()
            val password = editPassword.text.toString().trim()

            when {
                username.isEmpty() -> {
                    Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show()
                }
                password.isEmpty() -> {
                    Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
                }
                password.length < 6 -> {
                    Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                }
                dbHelper.isUsernameExists(username) -> {
                    Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val success = dbHelper.addUser(username, password)
                    if (success) {
                        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                        // Chuyển sang màn hình login
                        val intent = Intent(this, login::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Registration failed. Try again.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        tvLogin.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
            finish()
        }
    }
}