package com.example.loginbasic

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Button
import android.widget.TextView


class Home : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var tvWelcome: TextView
    private lateinit var tvUsername: TextView
    private lateinit var btnLogout: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        // Ánh xạ views
        tvWelcome = findViewById(R.id.tvWelcome)
        tvUsername = findViewById(R.id.tvUsername)
        btnLogout = findViewById(R.id.btnLogout)

        // Hiển thị username
        val username = sharedPreferences.getString("username", "User")
        tvUsername.text = "Logged in as: $username"

        // Xử lý logout
        btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        // Xóa trạng thái login
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        // Chuyển về màn hình login
        val intent = Intent(this, login::class.java)
        startActivity(intent)
        finish()
    }
}