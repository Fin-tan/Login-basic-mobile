package com.example.loginbasic

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.SharedPreferences
import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.edit

class login : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editUsername: EditText
    private lateinit var editPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvSignup: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        dbHelper = DatabaseHelper(this)
        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        // Kiểm tra xem user đã login chưa
        if (isUserLoggedIn()) {
            goToHome()
            return
        }

        // Ánh xạ views
        editUsername = findViewById(R.id.editusername)
        editPassword = findViewById(R.id.editpassword)
        btnLogin = findViewById(R.id.Login)
        tvSignup= findViewById(R.id.tvSignup)

        // Xử lý login
        btnLogin.setOnClickListener {
            val username = editUsername.text.toString().trim()
            val password = editPassword.text.toString().trim()

            when {
                username.isEmpty() -> {
                    Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show()
                }
                password.isEmpty() -> {
                    Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    if (dbHelper.checkUser(username, password)) {
                        // Lưu trạng thái login
                        saveLoginState(username)
                        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                        goToHome()
                    } else {
                        Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Chuyển sang màn hình signup
        tvSignup.setOnClickListener {
            val intent= Intent(this,signup::class.java)
            startActivity(intent)
            finish()

        }
    }

    private fun saveLoginState(username: String) {
        sharedPreferences.edit {
            putBoolean("isLoggedIn", true)
            putString("username", username)
        }
    }

    private fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    private fun goToHome() {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
        finish()
    }
}