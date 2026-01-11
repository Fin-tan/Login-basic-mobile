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
import com.example.loginbasic.SQLiteConnector
class signup : AppCompatActivity() {

    private lateinit var dbHelper: SQLiteConnector
    private lateinit var editEmail: EditText
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
        dbHelper=SQLiteConnector(this)

        editUsername=findViewById(R.id.editusername)
        editPassword=findViewById(R.id.editpassword)
        editEmail=findViewById(R.id.editemail)
        btnSignup=findViewById(R.id.btnSignup)
        tvLogin=findViewById<TextView>(R.id.tvLogin)
        btnSignup.setOnClickListener {
            val email    = editEmail.text.toString().trim()
            val username = editUsername.text.toString().trim()
            val password = editPassword.text.toString().trim()

            when {
                email.isEmpty() -> {
                    Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
                }
                username.isEmpty() -> {
                    Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show()
                }
                password.isEmpty() -> {
                    Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
                }
                password.length < 6 -> {
                    Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                }

                dbHelper.checkUser(email) -> {
                    Toast.makeText(this, "Email này đã tồn tại", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val newUser = User()
                    newUser.name = username  // Lưu ý: xem bên model User bạn đặt là name hay username
                    newUser.email = email
                    newUser.password = password


                    dbHelper.addUser(newUser)
                    Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, login::class.java)
                    startActivity(intent)
                    finish()
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