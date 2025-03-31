package com.example.usesqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "ContactDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE Contacts (ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Phone TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Contacts")
        onCreate(db)
    }

    // Thêm dữ liệu
    fun insertData(name: String, phone: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("Name", name)
        values.put("Phone", phone)
        val result = db.insert("Contacts", null, values)
        return result != -1L
    }

    // Cập nhật dữ liệu
    fun updateData(name: String, phone: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("Phone", phone)
        val result = db.update("Contacts", values, "Name=?", arrayOf(name))
        return result > 0
    }

    // Xóa dữ liệu
    fun deleteData(name: String): Boolean {
        val db = this.writableDatabase
        val result = db.delete("Contacts", "Name=?", arrayOf(name))
        return result > 0
    }

    // Lấy dữ liệu
    fun getAllData(): String {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Contacts", null)
        val result = StringBuilder()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val phone = cursor.getString(2)
                result.append("ID: $id - Tên: $name - SĐT: $phone\n")
            } while (cursor.moveToNext())
        }
        cursor.close()
        return result.toString()
    }
}
