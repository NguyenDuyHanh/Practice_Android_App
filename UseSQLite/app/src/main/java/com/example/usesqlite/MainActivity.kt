package com.example.usesqlite

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var helper: DatabaseHelper
    private lateinit var edtName: EditText
    private lateinit var edtPhone: EditText
    private lateinit var txtResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo database helper
        helper = DatabaseHelper(this)

        // Ánh xạ view
        edtName = findViewById(R.id.edtName)
        edtPhone = findViewById(R.id.edtPhone)
        txtResult = findViewById(R.id.txtResult)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        val btnDelete = findViewById<Button>(R.id.btnDelete)
        val btnShow = findViewById<Button>(R.id.btnShow)

        // Xử lý thêm dữ liệu
        btnAdd.setOnClickListener {
            val name = edtName.text.toString()
            val phone = edtPhone.text.toString()
            if (name.isNotEmpty() && phone.isNotEmpty()) {
                val isInserted = helper.insertData(name, phone)
                showToast(if (isInserted) "Thêm thành công!" else "Lỗi khi thêm!")
            } else {
                showToast("Vui lòng nhập đủ thông tin!")
            }
        }

        // Xử lý sửa dữ liệu
        btnUpdate.setOnClickListener {
            val name = edtName.text.toString()
            val phone = edtPhone.text.toString()
            val isUpdated = helper.updateData(name, phone)
            showToast(if (isUpdated) "Cập nhật thành công!" else "Không tìm thấy tên!")
        }

        // Xử lý xóa dữ liệu
        btnDelete.setOnClickListener {
            val name = edtName.text.toString()
            val isDeleted = helper.deleteData(name)
            txtResult.text = ""
            edtName.text.clear()
            edtPhone.text.clear()
            showToast(if (isDeleted) "Xóa thành công!" else "Không tìm thấy tên!")
        }

        // Xử lý hiển thị dữ liệu
        btnShow.setOnClickListener {
            txtResult.text = helper.getAllData()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
