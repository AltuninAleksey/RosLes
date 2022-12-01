package com.example.rosles

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.rosles.ResponceClass.ClassWood

class DBCountWood (context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                PORODA + " TEXT," +
                PROBA + " TEXT," +
                VIEWWOOD + " TEXT," +
                VALUE02 + " TEXT," +
                VALUE05 + " TEXT," +
                VALUE06 + " TEXT," +
                VALUE11 + " TEXT," +
                VALUE15 + " TEXT" + ")")




        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)


        db.execSQL("CREATE TABLE REPRODUCTION (id INTEGER PRIMARY KEY, REPRODUCTIONNAME TEXT)")
    }




    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }
    fun addReproduction(reproduction_name:String){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("REPRODUCTIONNAME",reproduction_name)
        db.insert("REPRODUCTION",null,values)
        db.close()
    }
    fun getReproduction(){

    }

    // This method is for adding data in our database
    fun addName( poroda : String,proba:String,
                viewwood : String, value02 : String,
                value05 : String, value06 : String,
                value11 : String, value15 : String){
        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair

        values.put(PORODA, poroda)
        values.put(PROBA, proba)
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
        val cursor = db.query("REPRODUCTION", arrayOf(column),null,null,null,null,null,null)
        with(cursor){
            while (this?.moveToNext()!!){
                val datatext = cursor.getString(cursor.getColumnIndexOrThrow(column))
                datalist.add(datatext.toString())
            }
        }
        return datalist
    }

    @SuppressLint("Range")
    fun readByID(id:String): ClassWood {
        var database:SQLiteDatabase = this.writableDatabase
        val cursor: Cursor =  database.rawQuery("SELECT * FROM " + TABLE_NAME.toString() + " WHERE " + ID_COL.toString() + "=" + id,
            null)
        cursor.moveToFirst()
        val wood:ClassWood = ClassWood(
            id,
            cursor.getString(cursor.getColumnIndex(PORODA)),
            cursor.getString(cursor.getColumnIndex(PROBA)),
            cursor.getString(cursor.getColumnIndex(VIEWWOOD)),
            cursor.getString(cursor.getColumnIndex(VALUE02)),
            cursor.getString(cursor.getColumnIndex(VALUE05)),
            cursor.getString(cursor.getColumnIndex(VALUE06)),
            cursor.getString(cursor.getColumnIndex(VALUE11)),
            cursor.getString(cursor.getColumnIndex(VALUE15))
            )
            return wood;
    }

    @SuppressLint("Range")
    fun readbyporoda(poroda:String): ClassWood {
        var database:SQLiteDatabase = this.writableDatabase
        val cursor: Cursor =  database.rawQuery("SELECT * FROM " + TABLE_NAME.toString() + " WHERE " + PORODA.toString() + "=" + poroda,
            null)
        cursor.moveToFirst()
        val wood:ClassWood = ClassWood(
            cursor.getString(cursor.getColumnIndex(ID_COL)),
            cursor.getString(cursor.getColumnIndex(PORODA)),
            cursor.getString(cursor.getColumnIndex(PROBA)),
            cursor.getString(cursor.getColumnIndex(VIEWWOOD)),
            cursor.getString(cursor.getColumnIndex(VALUE02)),
            cursor.getString(cursor.getColumnIndex(VALUE05)),
            cursor.getString(cursor.getColumnIndex(VALUE06)),
            cursor.getString(cursor.getColumnIndex(VALUE11)),
            cursor.getString(cursor.getColumnIndex(VALUE15))
        )
        return wood;
    }





    companion object{
        // here we have defined variables for our database
        // below is variable for database name
        private val DATABASE_NAME = "UsersDB.db"
        // below is the variable for database version
        private val DATABASE_VERSION = 1
        // below is the variable for table name
        val TABLE_NAME = "Woods"
        // below is the variable for id column
        val ID_COL = "id"
        // below is the variable for name column

        // below is the variable for age column
        val PORODA = "poroda"

        val PROBA = "proba"

        val VIEWWOOD = "viewwood"

        val VALUE02 = "value02"

        val VALUE05 = "value05"

        val VALUE06 = "value06"

        val VALUE11 = "value11"

        val VALUE15 = "value15"

        val arr = arrayOf(PORODA,VIEWWOOD,VALUE02,VALUE05,VALUE06,VALUE11,VALUE15)
    }
}


