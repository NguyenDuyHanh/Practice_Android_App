package com.example.practiceusefirebase


import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseReference

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("users")

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnShowData = findViewById<Button>(R.id.btnShowData)
        val tvUserData = findViewById<TextView>(R.id.tvUserData)

        // Xử lý đăng ký tài khoản
        btnRegister.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid
                            val userData = mapOf("email" to email)
                            userId?.let { database.child(it).setValue(userData) }
                            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Vui lòng nhập email và mật khẩu", Toast.LENGTH_SHORT).show()
            }
        }

        // Xử lý đăng nhập
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Vui lòng nhập email và mật khẩu", Toast.LENGTH_SHORT).show()
            }
        }

        btnShowData.setOnClickListener {
            hienThiDuLieu(tvUserData)
        }

    }

    // Hàm đọc toàn bộ dữ liệu từ Firebase Realtime Database
     private fun hienThiDuLieu(tvUserData: TextView) {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val stringBuilder = StringBuilder()
                for (userSnapshot in snapshot.children) {
                    val email = userSnapshot.child("email").getValue(String::class.java)
                    val password = userSnapshot.child("password").getValue(String::class.java)
                    stringBuilder.append("Email: $email\nMật khẩu: $password\n\n")
                }
                tvUserData.text = stringBuilder.toString() // Hiển thị dữ liệu lên TextView
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Lỗi đọc dữ liệu: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
