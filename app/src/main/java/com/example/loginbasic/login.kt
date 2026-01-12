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

        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        if (isUserLoggedIn()) {
            goToHome()
            return
        }

        editUsername = findViewById(R.id.editusername)
        editPassword = findViewById(R.id.editpassword)
        btnLogin = findViewById(R.id.Login)
        tvSignup = findViewById(R.id.tvSignup)

        btnLogin.setOnClickListener {
            val username = editUsername.text.toString().trim()
            val password = editPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Băm pass nhập vào để so sánh
            val hashedPassword = HashUtils.hashPassword(password)

            Thread {
                // PostgREST Filter: ?username=eq.abc&password=eq.xyz
                val url = "http://10.0.2.2:3000/users?username=eq.$username&password=eq.$hashedPassword"

                val result = NetworkUtils.sendRequest(url, "GET")

                runOnUiThread {
                    // Nếu tìm thấy, PostgREST trả về mảng JSON: [{"id":1, ...}]
                    // Nếu sai, trả về mảng rỗng: []

                    if (result.contains("\"username\"")) { // Kiểm tra xem có field username trong kết quả ko
                        saveLoginState(username)
                        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                        goToHome()
                    } else {
                        Toast.makeText(this, "Sai tài khoản hoặc mật khẩu (hoặc lỗi Server)", Toast.LENGTH_SHORT).show()
                    }
                }
            }.start()
        }

        tvSignup.setOnClickListener {
            val intent = Intent(this, signup::class.java)
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