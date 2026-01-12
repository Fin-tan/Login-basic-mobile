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

        editUsername = findViewById(R.id.editusername)
        editPassword = findViewById(R.id.editpassword)
        editEmail = findViewById(R.id.editemail)
        btnSignup = findViewById(R.id.btnSignup)
        tvLogin = findViewById(R.id.tvLogin)

        btnSignup.setOnClickListener {
            val email = editEmail.text.toString().trim()
            val username = editUsername.text.toString().trim()
            val password = editPassword.text.toString().trim()

            // Validate đơn giản
            if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Băm mật khẩu (Yêu cầu 4)
            val hashedPassword = HashUtils.hashPassword(password)

            // Xử lý gửi mạng (Yêu cầu 5)
            Thread {
                // Địa chỉ API PostgREST (10.0.2.2 thay cho localhost)
                val url = "http://10.0.2.2:3000/users"

                // Tạo JSON thủ công
                val jsonBody = """
                    {
                        "username": "$username",
                        "email": "$email",
                        "password": "$hashedPassword"
                    }
                """.trimIndent()

                val result = NetworkUtils.sendRequest(url, "POST", jsonBody)

                runOnUiThread {
                    // PostgREST trả về lỗi thường có từ "message" hoặc "code"
                    // Nếu thành công (201 Created), body có thể rỗng hoặc chứa data insert
                    if (!result.contains("error") && !result.contains("Error")) {
                        Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, login::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Lỗi đăng ký: $result", Toast.LENGTH_LONG).show()
                    }
                }
            }.start()
        }

        tvLogin.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
            finish()
        }
    }
}