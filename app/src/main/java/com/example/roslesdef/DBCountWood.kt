package com.example.roslesdef

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBCountWood (context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                PORODA + " TEXT," +
                VIEWWOOD + " TEXT," +
                VALUE02 + " TEXT," +
                VALUE05 + " TEXT," +
                VALUE06 + " TEXT," +
                VALUE11 + " TEXT," +
                VALUE15 + " TEXT" + ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    // This method is for adding data in our database
    fun addName( poroda : String,
                viewwood : String, value02 : String,
                value05 : String, value06 : String,
                value11 : String, value15 : String){
        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair

        values.put(PORODA, poroda)
        values.put(VIEWWOOD, viewwood)
        values.put(VALUE02, value02)
        values.put(VALUE05, value05)
        values.put(VALUE06, value06)
        values.put(VALUE11, value11)
        values.put(VALUE15, value15)



        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)

        // at last we are
        // closing our database
        db.close()
    }
    fun read(column: String): ArrayList<String>{
        val datalist = ArrayList<String>()
        val db = this.readableDatabase
        val cursor = db.query(TABLE_NAME, arrayOf(column),null,null,null,null,null,null)
        with(cursor){
            while (this?.moveToNext()!!){
                val datatext = cursor.getString(cursor.getColumnIndexOrThrow(column))
                datalist.add(datatext.toString())
            }
        }
        return datalist
    }
    companion object{
        // here we have defined variables for our database
        // below is variable for database name
        private val DATABASE_NAME = "UsersDB.db"
        // below is the variable for database version
        private val DATABASE_VERSION = 1
        // below is the variable for table name
        val TABLE_NAME = "users"
        // below is the variable for id column
        val ID_COL = "id"
        // below is the variable for name column

        // below is the variable for age column
        val PORODA = "poroda"

        val VIEWWOOD = "viewwood"

        val VALUE02 = "value02"

        val VALUE05 = "value05"

        val VALUE06 = "value06"

        val VALUE11 = "value11"

        val VALUE15 = "value15"

        val arr = arrayOf(PORODA,VIEWWOOD,VALUE02,VALUE05,VALUE06,VALUE11,VALUE15)
    }
}