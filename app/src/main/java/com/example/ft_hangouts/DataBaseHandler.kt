package com.example.ft_hangouts

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
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
      private const val TABLE_MESS = "Messages"
      private const val COL_SENDID = "send"
      private const val COL_RECID = "received"
      private const val COL_MESSAGE = "message"
      private const val COL_MESSAGEID = "messageId"
      private const val COL_DATE = "date"
  }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " VARCHAR(256), " +
                COL_PHONE + " VARCHAR(256));"

        val createMess = "CREATE TABLE IF NOT EXISTS " + TABLE_MESS + " (" +
                COL_SENDID + " INTEGER, " +
                COL_RECID + " INTEGER, " +
                COL_MESSAGE + " VARCHAR(256), " +
                COL_MESSAGEID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DATE + " VARCHAR(256)); "

        Log.e("createTable = ", createTable)
        Log.e("createTableMess = ", createMess)

        db?.execSQL(createTable)
        db?.execSQL(createMess)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MESS")
        onCreate(db)
    }

    fun deleteContact(contact: Contact) {
        Log.e("ppppdelete", "$contact.id")
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "id=" + contact.id, null)
        db.close()
    }

    fun deleteTable() {
        context.deleteDatabase(DATABASE_NAME)
    }

    fun deleteConv(contact: Contact) {
        Log.e("deleteConv", "$contact.id")
        val db = this.writableDatabase
        db.delete(TABLE_MESS, "received=" + contact.id, null)
        db.close()
    }

    fun newMessage(message: Message) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_SENDID, message.senderId)
        cv.put(COL_RECID, message.receiverId)
        cv.put(COL_MESSAGE, message.message)
        cv.put(COL_MESSAGEID, message.messageId)
        cv.put(COL_DATE, message.date)

        var result = db.insert(TABLE_MESS, null, cv)
        if (result == (-1).toLong())
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
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

    @SuppressLint("Range")
    fun getAllConv() : ArrayList<Contact> {
        val ctList: ArrayList<Contact> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_MESS"
        val db = this.readableDatabase

        val cursor: Cursor?
        try{
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var receivedId : Int

        if (cursor.moveToFirst()){
            do {
                receivedId = cursor.getInt(cursor.getColumnIndex("received"))
                val ct = getContact(receivedId)
                val find = ctList.contains(ct)
                if (!find && receivedId != 0) {
                    Log.e("getallconv name=", ct.name)
                    ctList.add(ct)
                }
            } while (cursor.moveToNext())
        }
        return ctList
    }

    @SuppressLint("Range")
    fun getMessages(recId: Int, sendId: Int) : ArrayList<Message> {
        val messList: ArrayList<Message> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_MESS"
        val db = this.readableDatabase

        val cursor: Cursor?
        try{
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var receivedId : Int
        var senderId : Int
        var message : String
        var messageId: Int
        var date : String

        if (cursor.moveToFirst()){
            do {
                receivedId = cursor.getInt(cursor.getColumnIndex("received"))
                senderId = cursor.getInt(cursor.getColumnIndex("send"))
                message = cursor.getString(cursor.getColumnIndex("message"))
                messageId = cursor.getInt(cursor.getColumnIndex("messageId"))
                date = cursor.getString(cursor.getColumnIndex("date"))

                Log.e("getmess =", receivedId.toString())
                if (receivedId == recId && senderId == 0 || (receivedId == sendId && senderId == recId)) {
                    val ct = Message(message, senderId, receivedId, messageId, date)
                    Log.e("getmess IIIIDDDD mess=", messageId.toString())
                    messList.add(ct)
                }
            } while (cursor.moveToNext())
            messList.sortBy{it.date}
        }
        return messList
    }

    @SuppressLint("Range")
    fun getContact(id: Int) : Contact {
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase

        val cursor: Cursor?
        try{
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return Contact()
        }

        var name: String
        var phone: String
        var receivedId : Int

        if (cursor.moveToFirst()) {
            do {
                receivedId = cursor!!.getInt(cursor.getColumnIndex("id"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                phone = cursor.getString(cursor.getColumnIndex("phone"))
                if (receivedId == id) {
                    return Contact(id = receivedId, name = name, phone = phone)
                }
            } while (cursor?.moveToNext()!!)
        }
        return Contact()
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