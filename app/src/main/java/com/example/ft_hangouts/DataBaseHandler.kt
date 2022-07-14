package com.example.ft_hangouts

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build.ID
import android.util.Log
import android.widget.Toast
import java.lang.Exception

const val DATABASE_NAME = "MyDB"
//val TABLE_NAME = "Users"
//val COL_NAME = "name"
//val COL_PHONE = "phone"
//val COL_ID = "id"

class DataBaseHandler (var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

  //  private const val DATABASE_NAME = "MyDB"
    companion object {
      private const val TABLE_NAME = "Users"
      private const val COL_NAME = "name"
      private const val COL_PHONE = "phone"
      private const val COL_ID = "id"
  }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " VARCHAR(256), " +
                COL_PHONE + " VARCHAR(256))";

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun deleteContact(contact: Contact) {
        Log.e("ppppdelete", "$contact.id")
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "id=" + contact.id, null)
        db.close()
    }

    fun insertContact(contact: Contact) : Long {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_NAME, contact.name)
        cv.put(COL_PHONE, contact.phone)
        var result = db.insert(TABLE_NAME, null, cv)
        if (result == (-1).toLong())
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        db.close()
        return result
    }

    @SuppressLint("Range")
    fun getAllContact() : ArrayList<Contact> {
        val ctList: ArrayList<Contact> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase

        val cursor: Cursor?

        try{
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id : Int
        var name: String
        var phone: String

        if (cursor.moveToFirst()){
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                phone = cursor.getString(cursor.getColumnIndex("phone"))

                val ct = Contact(id = id, name = name, phone = phone)
                ctList.add(ct)
            } while (cursor.moveToNext())
        }
        return ctList
    }

    //fun updateContact(ct: ContactAdapter.ContactViewHolder) : Int {
    fun updateContact( contact: Contact) : Int {
        val db = this.writableDatabase

        Log.e("updateContact", contact.id.toString())
        var cv = ContentValues()
        cv.put(COL_ID, contact.id)
        cv.put(COL_NAME, contact.name)
        cv.put(COL_PHONE, contact.phone)

        val success = db.update(TABLE_NAME, cv, "id=" + contact.id.toString(), null)
        db.close()
        return success
    }
}