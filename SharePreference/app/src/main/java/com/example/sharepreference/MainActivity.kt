package com.example.sharepreference

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var preferenceHelper: PreferenceHelper
    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var txtResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        // Khởi tạo Shared Preferences Helper
        preferenceHelper = PreferenceHelper(this)

        // Ánh xạ View
        edtUsername = findViewById(R.id.edtUsername)
        edtPassword = findViewById(R.id.edtPassword)
        txtResult = findViewById(R.id.txtResult)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnShow = findViewById<Button>(R.id.btnShow)
        val btnClear = findViewById<Button>(R.id.btnClear)

        // Xử lý sự kiện khi nhấn "Lưu"
        btnSave.setOnClickListener {
            val username = edtUsername.text.toString()
            val password = edtPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                preferenceHelper.saveUserData(username, password)
                Toast.makeText(this, "Đã lưu thành công!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
            }
        }

        // Xử lý sự kiện khi nhấn "Hiển thị"
        btnShow.setOnClickListener {
            val (username, password) = preferenceHelper.getUserData()
            txtResult.text = if (username != null && password != null) {
                "Tên: $username\nMật khẩu: $password"
            } else {
                "Không có dữ liệu được lưu!"
            }
        }

        // Xử lý sự kiện khi nhấn "Xóa"
        btnClear.setOnClickListener {
            preferenceHelper.clearUserData()
            txtResult.text = ""
            edtUsername.text.clear()
            edtPassword.text.clear()
            Toast.makeText(this, "Dữ liệu đã được xóa!", Toast.LENGTH_SHORT).show()
        }
    }
}