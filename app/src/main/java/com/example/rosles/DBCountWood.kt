package com.example.rosles

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import androidx.core.database.getIntOrNull
import com.example.rosles.Models.*
import com.example.rosles.ResponceClass.*
import com.example.rosles.Screens.GPStracker
import com.example.roslesdef.Models.SpinerItem
import java.util.*


class DBCountWood(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    val context=context
    var gpStracker= GPStracker(context)
    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL("""CREATE TABLE IF NOT EXISTS "djangoForest_breed" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "name_breed" varchar(350) NOT NULL, "is_foliar" bool NULL, "is_pine" bool NULL, "ShortName" varchar(10) NULL);""")
        db.execSQL("""CREATE TABLE IF NOT EXISTS "djangoForest_districtforestly" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "name_district_forestly" varchar(500) NOT NULL, "id_forestly_id" bigint NULL REFERENCES "djangoForest_forestly" ("id") DEFERRABLE INITIALLY DEFERRED);""")
        db.execSQL("""CREATE TABLE IF NOT EXISTS "djangoForest_forestformingbydefault" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "id_breed_id" bigint NOT NULL REFERENCES "djangoForest_breed" ("id") DEFERRABLE INITIALLY DEFERRED, "id_profile_id" bigint NOT NULL REFERENCES "djangoForest_profile" ("id") DEFERRABLE INITIALLY DEFERRED);""")
        db.execSQL("""CREATE TABLE IF NOT EXISTS "djangoForest_forestly" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "name_forestly" varchar(500) NOT NULL, "id_subject_rf_id" bigint NULL REFERENCES "djangoForest_subjectrf" ("id") DEFERRABLE INITIALLY DEFERRED);""")
        db.execSQL("""CREATE TABLE IF NOT EXISTS "djangoForest_gps" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "latitude" real NOT NULL, "longitude" real NOT NULL, "flag_center" integer NOT NULL, "id_sample_id" bigint NULL REFERENCES "djangoForest_sample" ("id") DEFERRABLE INITIALLY DEFERRED);""")
        db.execSQL("""CREATE TABLE IF NOT EXISTS "djangoForest_list" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "to0_2" integer NULL, "from0_21To0_5" integer NULL, "from0_6To1_0" integer NULL, "from1_1to1_5" integer NULL, "from1_5" integer NULL, "max_height" real NULL, "id_breed_id" bigint NULL REFERENCES "djangoForest_breed" ("id") DEFERRABLE INITIALLY DEFERRED, "id_sample_id" bigint NULL REFERENCES "djangoForest_sample" ("id") ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, "id_type_of_reproduction_id" bigint NULL REFERENCES "djangoForest_reproduction" ("id") DEFERRABLE INITIALLY DEFERRED, "avg_diameter" real NULL, "avg_height" real NULL, "count_of_plants" integer NULL, "id_undergrowth_id" bigint NULL REFERENCES "djangoForest_undergrowth" ("id") DEFERRABLE INITIALLY DEFERRED, "main" bool NULL, "avg_height_undergrowth" real NULL, mark_update INTEGER);""")
        db.execSQL("""CREATE TABLE IF NOT EXISTS "djangoForest_listregion" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "date" date NOT NULL, "sample_region" real NOT NULL, "soil_lot" varchar(300) NOT NULL, "id_quarter_id" bigint NULL REFERENCES "djangoForest_quarter" ("id") DEFERRABLE INITIALLY DEFERRED, "mark_del" integer NULL, "mark_update" integer NULL,  "id_profile" integer, "number_region" varchar);""")
        db.execSQL("""CREATE TABLE IF NOT EXISTS "djangoForest_photopoint" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "photo" BLOB,"latitude" real,"longitude" real,"date" date NOT NULL, "id_sample_id" bigint NULL REFERENCES "djangoForest_sample" ("id") DEFERRABLE INITIALLY DEFERRED);""")
        db.execSQL("""CREATE TABLE IF NOT EXISTS "djangoForest_profile" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "FIO" varchar(255) NOT NULL, "phoneNumber" varchar(30) NOT NULL, "id_branches_id" bigint NULL REFERENCES "djangoForest_branches" ("id") DEFERRABLE INITIALLY DEFERRED, "id_post_id" bigint NULL REFERENCES "djangoForest_post" ("id") DEFERRABLE INITIALLY DEFERRED, "id_role_id" bigint NULL REFERENCES "djangoForest_role" ("id") DEFERRABLE INITIALLY DEFERRED, "id_user_id" bigint NULL REFERENCES "djangoForest_users" ("id") DEFERRABLE INITIALLY DEFERRED);""")
        db.execSQL("""CREATE TABLE IF NOT EXISTS "djangoForest_quarter" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "quarter_name" varchar(50) NOT NULL, "id_district_forestly_id" bigint NULL REFERENCES "djangoForest_districtforestly" ("id") DEFERRABLE INITIALLY DEFERRED);""")
        db.execSQL("""CREATE TABLE IF NOT EXISTS "djangoForest_reproduction" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "name_reproduction" varchar(500) NOT NULL);""")
        db.execSQL("""CREATE TABLE IF NOT EXISTS "djangoForest_role" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "name_role" varchar(300) NOT NULL);""")
        db.execSQL("""CREATE TABLE IF NOT EXISTS "djangoForest_sample" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "date" date NULL, "sample_area" real NOT NULL, "id_list_region_id" bigint NULL REFERENCES "djangoForest_listregion" ("id") ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, "id_profile_id" bigint NULL REFERENCES "djangoForest_profile" ("id") DEFERRABLE INITIALLY DEFERRED, "id_quarter_id" bigint NULL REFERENCES "djangoForest_quarter" ("id") DEFERRABLE INITIALLY DEFERRED, "soil_lot" varchar(300) NOT NULL, "lenght" real NULL, "square" real NULL, "width" real NULL,mark_update INTEGER);""")
        db.execSQL("""CREATE TABLE IF NOT EXISTS "djangoForest_subjectrf" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "name_subject_RF" varchar(255) NOT NULL);""")
        db.execSQL("""CREATE TABLE IF NOT EXISTS "djangoForest_table" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "name" varchar(300) NOT NULL, "age" integer NOT NULL);""")
        db.execSQL("""CREATE TABLE IF NOT EXISTS "djangoForest_track" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "data" date NOT NULL, "map" varchar(1) NOT NULL, "id_profile_id" bigint NOT NULL REFERENCES "djangoForest_profile" ("id") DEFERRABLE INITIALLY DEFERRED);""")
        db.execSQL("""CREATE TABLE IF NOT EXISTS "djangoForest_undergrowth" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "name" varchar(350) NOT NULL);""")
        db.execSQL("""CREATE TABLE IF NOT EXISTS "djangoForest_undergrowthbydefault" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "id_profile_id" bigint NOT NULL REFERENCES "djangoForest_profile" ("id") DEFERRABLE INITIALLY DEFERRED, "id_undergrowth_id" bigint NOT NULL REFERENCES "djangoForest_undergrowth" ("id") DEFERRABLE INITIALLY DEFERRED);""")
        db.execSQL("""CREATE INDEX IF NOT EXISTS "djangoForest_districtforestly_id_forestly_id_8d57b4f6" ON "djangoForest_districtforestly" ("id_forestly_id");""")
        db.execSQL("""CREATE INDEX IF NOT EXISTS "djangoForest_forestformingbydefault_id_breed_id_4a07abd1" ON "djangoForest_forestformingbydefault" ("id_breed_id");""")
        db.execSQL("""CREATE INDEX IF NOT EXISTS "djangoForest_forestformingbydefault_id_profile_id_1a43bad2" ON "djangoForest_forestformingbydefault" ("id_profile_id");""")
        db.execSQL("""CREATE INDEX IF NOT EXISTS "djangoForest_forestly_id_subject_rf_id_db160798" ON "djangoForest_forestly" ("id_subject_rf_id");""")
        db.execSQL("""CREATE INDEX IF NOT EXISTS "djangoForest_gps_id_sample_id_6877d75e" ON "djangoForest_gps" ("id_sample_id");""")
        db.execSQL("""CREATE INDEX IF NOT EXISTS "djangoForest_list_id_breed_id_818099fb" ON "djangoForest_list" ("id_breed_id");""")
        db.execSQL("""CREATE INDEX IF NOT EXISTS "djangoForest_list_id_sample_id_a446b4c9" ON "djangoForest_list" ("id_sample_id");""")
        db.execSQL("""CREATE INDEX IF NOT EXISTS "djangoForest_list_id_type_of_reproduction_id_3f515d72" ON "djangoForest_list" ("id_type_of_reproduction_id");""")
        db.execSQL("""CREATE INDEX IF NOT EXISTS "djangoForest_list_id_undergrowth_id_02d0f36b" ON "djangoForest_list" ("id_undergrowth_id");""")
        db.execSQL("""CREATE INDEX IF NOT EXISTS "djangoForest_listregion_id_quarter_id_cd3b2ab8" ON "djangoForest_listregion" ("id_quarter_id");""")
        db.execSQL("""CREATE INDEX IF NOT EXISTS "djangoForest_photopoint_id_sample_id_9a160505" ON "djangoForest_photopoint" ("id_sample_id");""")
        db.execSQL("""CREATE INDEX IF NOT EXISTS "djangoForest_profile_id_branches_id_ecc09e8c" ON "djangoForest_profile" ("id_branches_id");""")
        db.execSQL("""CREATE INDEX IF NOT EXISTS "djangoForest_profile_id_post_id_3ebc259a" ON "djangoForest_profile" ("id_post_id");""")
        db.execSQL("""CREATE INDEX IF NOT EXISTS "djangoForest_profile_id_role_id_a92c9632" ON "djangoForest_profile" ("id_role_id");""")
        db.execSQL("""CREATE INDEX IF NOT EXISTS "djangoForest_profile_id_user_id_a5391657" ON "djangoForest_profile" ("id_user_id");""")
        db.execSQL("""CREATE INDEX IF NOT EXISTS "djangoForest_quarter_id_district_forestly_id_71afc36f" ON "djangoForest_quarter" ("id_district_forestly_id");""")
        db.execSQL("""CREATE INDEX IF NOT EXISTS "djangoForest_sample_id_list_region_id_d4ea853d" ON "djangoForest_sample" ("id_list_region_id");""")
        db.execSQL("""CREATE INDEX IF NOT EXISTS "djangoForest_sample_id_profile_id_326ca965" ON "djangoForest_sample" ("id_profile_id");""")
        db.execSQL("""CREATE INDEX IF NOT EXISTS "djangoForest_sample_id_quarter_id_eb7f4fa2" ON "djangoForest_sample" ("id_quarter_id");""")
        db.execSQL("""CREATE INDEX IF NOT EXISTS "djangoForest_track_id_profile_id_07b234ed" ON "djangoForest_track" ("id_profile_id");""")
        db.execSQL("""CREATE INDEX IF NOT EXISTS "djangoForest_undergrowthbydefault_id_profile_id_23b572fe" ON "djangoForest_undergrowthbydefault" ("id_profile_id");""")
        db.execSQL("""CREATE INDEX IF NOT EXISTS "djangoForest_undergrowthbydefault_id_undergrowth_id_b789efb4" ON "djangoForest_undergrowthbydefault" ("id_undergrowth_id");""")



        db.execSQL("""CREATE TABLE IF NOT EXISTS "delte_value" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "name_table" varchar(350) NOT NULL, "id_value" integer NULL );""")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }


    fun djangoForest_breed():Boolean{
        val database: SQLiteDatabase = this.readableDatabase
        val cursor: Cursor = database.rawQuery("select * from djangoForest_breed",null)
        return cursor.count > 0
    }
    fun djangoForest_districtforestly():Boolean{
        val database: SQLiteDatabase = this.readableDatabase
        val cursor: Cursor = database.rawQuery("select * from djangoForest_districtforestly",null)
        return cursor.count > 0
    }

    fun djangoForest_forestly():Boolean{
        val database: SQLiteDatabase = this.readableDatabase
        val cursor: Cursor = database.rawQuery("select * from djangoForest_forestly",null)
        return cursor.count > 0
    }


    fun djangoForest_list():Boolean{
        val database: SQLiteDatabase = this.readableDatabase
        val cursor: Cursor = database.rawQuery("select * from djangoForest_list",null)
        return cursor.count > 0
    }
    fun djangoForest_listregion():Boolean{
        val database: SQLiteDatabase = this.readableDatabase
        val cursor: Cursor = database.rawQuery("select * from djangoForest_listregion",null)
        return cursor.count > 0
    }

    fun djangoForest_quarter():Boolean{
        val database: SQLiteDatabase = this.readableDatabase
        val cursor: Cursor = database.rawQuery("select * from djangoForest_quarter",null)
        return cursor.count > 0
    }


    fun djangoForest_sample():Boolean{
        val database: SQLiteDatabase = this.readableDatabase
        val cursor: Cursor = database.rawQuery("select * from djangoForest_sample",null)
        return cursor.count > 0
    }
    fun djangoForest_subjectrf():Boolean{
        val database: SQLiteDatabase = this.readableDatabase
        val cursor: Cursor = database.rawQuery("select * from djangoForest_subjectrf",null)
        return cursor.count > 0
    }
    fun djangoForest_undergrowth():Boolean{
        val database: SQLiteDatabase = this.readableDatabase
        val cursor: Cursor = database.rawQuery("select * from djangoForest_undergrowth",null)
        return cursor.count > 0
    }



    fun writeLISTREGION(
        id:Int,
        date: String, sample_region: Float?, id_quarter_id: Int, soil_lot: String,
        mark_update: Int?,id_profile: Int?,number_region: String?) {
        val db = this.writableDatabase
        //Log.v(date,"")
        db.execSQL(
            "Insert into djangoForest_listregion\n" +
                    "(id,date, sample_region, id_quarter_id, soil_lot, mark_update,id_profile,number_region)  \n" +
                    "values ('$id','$date', '$sample_region', '$id_quarter_id', '$soil_lot','$mark_update','$id_profile','$number_region')"
        )
        db.close()
    }



    fun writeLIST(id:Int,to0_2:Int, from0_21To0_5:Int, from0_6To1_0:Int, from1_1to1_5:Int, from1_5:Int, max_height:Float?, id_breed_id:Int, id_sample_id:Int, id_type_of_reproduction_id:Int, avg_diameter:Float?, avg_height:Float?, count_of_plants:Int?, id_undergrowth_id:Int?, main:Int?, avg_height_undergrowth:Float?){
        val database: SQLiteDatabase = this.writableDatabase
        database.execSQL(
            "INSERT INTO djangoForest_list (id,to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main, avg_height_undergrowth)" +
                    " VALUES ('$id',$to0_2, $from0_21To0_5, $from0_6To1_0, $from1_1to1_5, $from1_5, $max_height, $id_breed_id, $id_sample_id, $id_type_of_reproduction_id, $avg_diameter, $avg_height, $count_of_plants, $id_undergrowth_id, '$main', $avg_height_undergrowth)"
        )
        database.close()
    }
    fun writeBREED(id:Int,name_breed:String, is_foliar:Boolean, is_pine:Boolean, ShortName:String?){
        val database: SQLiteDatabase = this.writableDatabase
        database.execSQL(
            "INSERT INTO djangoForest_breed (id,name_breed, is_foliar, is_pine, ShortName) VALUES ('$id','$name_breed', '$is_foliar', '$is_pine', '$ShortName')"
        )
        database.close()
    }
    fun writeSAMPLE(id:Int,date:String, sample_area:Float?, id_list_region_id:Int, id_profile_id:Int, id_quarter_id:Int, soil_lot:String, lenght:Int?, square:Int?, width:Int?){
        val database: SQLiteDatabase = this.writableDatabase
        database.execSQL(
            "INSERT INTO djangoForest_sample (id,date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) " +
                    "VALUES ('$id','$date', $sample_area, $id_list_region_id, $id_profile_id, $id_quarter_id, '$soil_lot', $lenght, $square, $width)"
        )
        database.close()
    }
    fun writeSUBJECTRF(id:Int,name_subject_RF:String){
        val database: SQLiteDatabase = this.writableDatabase
        database.execSQL(
            "INSERT INTO djangoForest_subjectrf (id,name_subject_RF) VALUES ('$id','$name_subject_RF')"
        )
        database.close()
    }
    fun writeFORESTLY(id:Int,name_forestly:String,id_subject_rf_id:Int){
        val database: SQLiteDatabase = this.writableDatabase
        database.execSQL(
            "INSERT INTO djangoForest_forestly (id,name_forestly, id_subject_rf_id) VALUES('$id','$name_forestly',$id_subject_rf_id)"
        )
        database.close()
    }
    fun writeDISTRICT(id:Int,name_district_forestly:String,id_forestly_id:Int){
        val database: SQLiteDatabase = this.writableDatabase
        database.execSQL(
            "INSERT INTO djangoForest_districtforestly (id,name_district_forestly, id_forestly_id) VALUES ('$id','$name_district_forestly',$id_forestly_id)"
        )
        database.close()
    }
    fun writeQUATER(id:Int,quarter_name:String,id_district_forestly_id:Int){
        val database: SQLiteDatabase = this.writableDatabase
        database.execSQL(
            "INSERT INTO djangoForest_quarter (id,quarter_name, id_district_forestly_id) VALUES ('$id','$quarter_name',$id_district_forestly_id)"
        )
        database.close()
    }
    fun writeundergrowth(id:Int,name:String){
        val database: SQLiteDatabase = this.writableDatabase
        database.execSQL(
            "INSERT INTO djangoForest_undergrowth (id,name) VALUES('$id','$name')"
        )
        database.close()
    }

    @SuppressLint("Range")
    fun getLISTREGION():List<LISTREGION_DATA>{
        val database: SQLiteDatabase = this.writableDatabase




        val cursor: Cursor = database.rawQuery("select * from djangoForest_listregion where mark_update =  2 OR mark_update = 1 ",null)
        cursor.moveToFirst()
        val listregionrequest=mutableListOf<LISTREGION_DATA>()
        for (i in 1..cursor.getCount()) {
            val data= LISTREGION_DATA(
                cursor.getString(cursor.getColumnIndex("id")).toInt(),
                cursor.getString(cursor.getColumnIndex("date")).toString(),
                cursor.getString(cursor.getColumnIndex("sample_region")).toFloatOrNull(),
                cursor.getString(cursor.getColumnIndex("soil_lot")).toString(),
               0,
                cursor.getInt(cursor.getColumnIndex("mark_update")),
                cursor.getInt(cursor.getColumnIndex("id_quarter_id")),
                cursor.getInt(cursor.getColumnIndex("id_profile")),
                cursor.getString(cursor.getColumnIndex("number_region"))
            )
            listregionrequest.add(data)

//            val id=cursor.getString(cursor.getColumnIndex("id")).toInt()
//            val date=cursor.getString(cursor.getColumnIndex("date")).toInt()
//            val soil_lot=cursor.getString(cursor.getColumnIndex("soil_lot")).toFloatOrNull()
//            val id_quarter_id=cursor.getString(cursor.getColumnIndex("id_quarter_id")).toInt()
//            val mark_del=cursor.getString(cursor.getColumnIndex("mark_del")).toInt()
//            val sample_region=cursor.getString(cursor.getColumnIndex("sample_region")).toFloatOrNull()
//            val mark_update=cursor.getString(cursor.getColumnIndex("mark_update")).toInt()

            cursor.moveToNext()
        }
        cursor.close()
        return listregionrequest
    }
    @SuppressLint("Range")
    fun getSAMPLEbyID_Listregion(id: Int):List<SAMPLE_DATA>{
        val database: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = database.rawQuery("select * from djangoForest_sample where id_list_region_id = $id",null)
        cursor.moveToFirst()
        val listregionrequest=mutableListOf<SAMPLE_DATA>()
        for (i in 1..cursor.getCount()) {

            val data= SAMPLE_DATA(
                cursor.getString(cursor.getColumnIndex("id")).toInt(),
                cursor.getString(cursor.getColumnIndex("date")).toString(),
                cursor.getFloat(cursor.getColumnIndex("sample_area")),
                cursor.getString(cursor.getColumnIndex("soil_lot")),
                cursor.getInt(cursor.getColumnIndex("width")),
                cursor.getInt(cursor.getColumnIndex("lenght")),
                cursor.getInt(cursor.getColumnIndex("square")),
                cursor.getInt(cursor.getColumnIndex("id_profile_id")).toInt(),
                cursor.getInt(cursor.getColumnIndex("id_list_region_id")).toInt(),
                cursor.getInt(cursor.getColumnIndex("id_quarter_id")).toInt(),
                cursor.getInt(cursor.getColumnIndex("mark_update"))

            )
            listregionrequest.add(data)
            cursor.moveToNext()
        }
        cursor.close()
        return listregionrequest
    }


    @SuppressLint("Range")
    fun getSAMPLE(id: Int):List<SAMPLE_DATA>{
        val database: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = database.rawQuery("select * from djangoForest_sample where id = $id",null)
        cursor.moveToFirst()
        val listregionrequest=mutableListOf<SAMPLE_DATA>()
        for (i in 1..cursor.getCount()) {

            val data= SAMPLE_DATA(
                cursor.getString(cursor.getColumnIndex("id")).toInt(),
                cursor.getString(cursor.getColumnIndex("date")).toString(),
                cursor.getFloat(cursor.getColumnIndex("sample_area")),
                cursor.getString(cursor.getColumnIndex("soil_lot")),
                cursor.getInt(cursor.getColumnIndex("width")),
                cursor.getInt(cursor.getColumnIndex("lenght")),
                cursor.getInt(cursor.getColumnIndex("square")),
                cursor.getInt(cursor.getColumnIndex("id_profile_id")).toInt(),
                cursor.getInt(cursor.getColumnIndex("id_list_region_id")).toInt(),
                cursor.getInt(cursor.getColumnIndex("id_quarter_id")).toInt(),
                cursor.getInt(cursor.getColumnIndex("mark_update"))

            )
            listregionrequest.add(data)
            cursor.moveToNext()
        }
        cursor.close()
        return listregionrequest
    }

    @SuppressLint("Range")
    fun getLIST(id: Int):List<LIST_DATA>{
        val database: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = database.rawQuery("select * from djangoForest_list where id_sample_id = $id",null)
        cursor.moveToFirst()
        val listregionrequest=mutableListOf<LIST_DATA>()
        for (i in 1..cursor.getCount()) {
            val data= LIST_DATA(
                cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getInt(cursor.getColumnIndex("to0_2")),
                cursor.getInt(cursor.getColumnIndex("from0_21To0_5")).toInt(),
                cursor.getInt(cursor.getColumnIndex("from0_6To1_0")).toInt(),
                cursor.getInt(cursor.getColumnIndex("from1_1to1_5")).toInt(),
                cursor.getInt(cursor.getColumnIndex("from1_5")).toInt(),
                cursor.getFloat(cursor.getColumnIndex("max_height")),
                cursor.getFloat(cursor.getColumnIndex("avg_diameter")),
                cursor.getInt(cursor.getColumnIndex("count_of_plants")).toInt(),
                cursor.getFloat(cursor.getColumnIndex("avg_height")),
                cursor.getFloat(cursor.getColumnIndex("avg_height_undergrowth")),
                cursor.getInt(cursor.getColumnIndex("main")),
                cursor.getInt(cursor.getColumnIndex("id_sample_id")).toInt(),
                cursor.getInt(cursor.getColumnIndex("id_breed_id")).toInt(),
                cursor.getInt(cursor.getColumnIndex("id_type_of_reproduction_id")).toInt() ,
                cursor.getIntOrNull(cursor.getColumnIndex("id_undergrowth_id")),
                cursor.getInt(cursor.getColumnIndex("mark_update"))
            )
            listregionrequest.add(data)
            cursor.moveToNext()
        }
        cursor.close()
        return listregionrequest
    }


    @SuppressLint("Range")
    fun Mark_Update_Listregion(id: Int){
        val database: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = database.rawQuery("select * from djangoForest_listregion where id='$id'",null)
        cursor.moveToFirst()
        val buf=cursor.getInt(cursor.getColumnIndex("mark_update"))
        if(cursor.getInt(cursor.getColumnIndex("mark_update")) <= 1){
            database.execSQL(
                "update djangoForest_listregion set mark_update = 1 where id = $id"
            )
        }

    }
    @SuppressLint("Range")
    fun Mark_Update_Sample(id: Int){
        val database: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = database.rawQuery("select * from djangoForest_sample where id='$id'",null)
        cursor.moveToFirst()
        if(cursor.getInt(cursor.getColumnIndex("mark_update")) <= 1){
            database.execSQL(
                "update djangoForest_sample set mark_update = 1 where id = $id"
            )
        }

    }





    @SuppressLint("Range")
    fun Mark_Update_List(id: Int) {
        val database: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor =
            database.rawQuery("select * from djangoForest_list where id='$id'", null)
        cursor.moveToFirst()
        if (cursor.getInt(cursor.getColumnIndex("mark_update")) <= 1) {
            database.execSQL(
                "update djangoForest_list set mark_update = 1 where id = $id"
            )
        }
    }

    fun Synck_Update_Listregion(id: Int){
        val database: SQLiteDatabase = this.writableDatabase
        database.execSQL(
            "update djangoForest_listregion set mark_update = 0 where id = $id"
        )
    }

    fun Synck_Update_All(){
        val database: SQLiteDatabase = this.writableDatabase
        database.execSQL(
            "update djangoForest_listregion set mark_update = 0 where mark_update > 0"
        )
        database.execSQL(
            "update djangoForest_sample set mark_update = 0 where mark_update > 0"
        )
        database.execSQL(
            "update djangoForest_list set mark_update = 0 where mark_update > 0"
        )
    }


    @SuppressLint("Range")
    fun readbyporoda(): List<Poroda> {
        val database: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = database.rawQuery(
            """select listregion.id,listregion.mark_update, s3.name_forestly, s3.name_district_forestly, s3.quarter_name, s3.soil_lot, s3.sample_region, s3.date
                    from (((djangoForest_listregion as listregion  INNER join djangoForest_quarter as quartal on listregion.id_quarter_id = quartal.id) as s1
                    inner join djangoForest_districtforestly as district on s1.id_district_forestly_id = district.id) as s2
                    inner join djangoForest_forestly as forestly on s2.id_forestly_id = forestly.id) as s3""",
            null
        )
        val porodaList: MutableList<Poroda> = mutableListOf<Poroda>();
        cursor.moveToFirst()

        for (i in 1..cursor.count) {
            val poroda = Poroda(
                cursor.getString(cursor.getColumnIndex("name_forestly")),
                cursor.getString(cursor.getColumnIndex("name_district_forestly")),
                cursor.getString(cursor.getColumnIndex("quarter_name")),
                cursor.getString(cursor.getColumnIndex("soil_lot")),
                cursor.getString(cursor.getColumnIndex("date")),
                cursor.getString(cursor.getColumnIndex("id")),
                cursor.getInt(cursor.getColumnIndex("mark_update"))  // Q2
            )
            porodaList.add(poroda)
            cursor.moveToNext()
        }
        cursor.close()
        database.close()

        return porodaList
    }
    @SuppressLint("Range")
    fun getsubject(): List<Subject> {
        val database: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor =
            database.rawQuery("SELECT id,name_subject_RF FROM djangoForest_subjectrf", null)

        val subjectsList = mutableListOf<Subject>()
        cursor.moveToFirst()
        for (i in 1..cursor.count) {
            val subject = Subject(
                cursor.getString(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("name_subject_RF"))
            )
            subjectsList.add(subject)
            cursor.moveToNext()
        }
        cursor.close()
        database.close()

        return subjectsList
    }

    @SuppressLint("Range")
    fun getLesnich(id_subject_rf: Int): List<Lesnich> {
        val database: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = database.rawQuery(
            "SELECT * FROM djangoForest_forestly as s1 WHERE s1.id_subject_rf_id = $id_subject_rf;",
            null
        )
        val lesnichList = mutableListOf<Lesnich>()
        cursor.moveToFirst()

        for (i in 1 .. cursor.count) {
            val lesnich = Lesnich(
                cursor.getString(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("name_forestly"))
            )
            lesnichList.add(lesnich)
            cursor.moveToNext()
        }

        cursor.close()
        database.close()
        return lesnichList
    }

    @SuppressLint("Range")
    fun getDistrict(id_forestly: Int): List<District> {
        val database: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = database.rawQuery(
            "SELECT * FROM djangoForest_districtforestly as s2 WHERE s2.id_forestly_id = $id_forestly;",
            null
        )
        val districtList = mutableListOf<District>()
        cursor.moveToFirst()

        for (i in 1 .. cursor.count) {
            val district = District(
                cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("name_district_forestly"))
            )
            districtList.add(district)
            cursor.moveToNext()
        }

        cursor.close()
        database.close()
        return districtList

    }

    @SuppressLint("Range")
    fun getQuater(id_district: Int): List<Quater> {
        val database: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = database.rawQuery(
            "SELECT * FROM djangoForest_quarter as s3 WHERE  s3.id_district_forestly_id = $id_district;",
            null
        )
        val quaterList = mutableListOf<Quater>()
        cursor.moveToFirst()

        for (i in 1 .. cursor.count) {
            val quater = Quater(
                cursor.getString(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("quarter_name"))
            )
            quaterList.add(quater)
            cursor.moveToNext()
        }

        cursor.close()
        database.close()

        return quaterList
    }
    fun Updatevedom(
        id: Int,
        date: String,
        sample_region: String,
        id_quarter_id: Int,
        soil_lot: String
    ) {
        val db = this.writableDatabase
        //Log.v(date,"")
        db.execSQL("Update djangoForest_listregion set date = '$date', sample_region = '$sample_region', id_quarter_id = $id_quarter_id, soil_lot = '$soil_lot'  where id = $id")
        db.close()
    }
    @SuppressLint("Range")
    fun getQuaterbyID(id: Int?): Quater {
        val database: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = database.rawQuery(
            "select id, quarter_name, id_district_forestly_id from djangoForest_quarter where id = $id",
            null
        )
        cursor.moveToFirst()
        val quater = Quater(
            cursor.getString(cursor.getColumnIndex("id")),
            cursor.getString(cursor.getColumnIndex("quarter_name"))
        )

        cursor.close()
        database.close()
        return quater

    }

    @SuppressLint("Range")
    fun getVedombyID(id: Int?): Vedom {
        val database: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = database.rawQuery(
            "select listregion.id, s3.name_forestly, s3.name_district_forestly, s3.quarter_name, s3.id_quarter_id, s3.soil_lot, s3.sample_region, s3.date  \n" +
                    "from (((djangoForest_listregion as listregion inner join djangoForest_quarter as quartal on listregion.id_quarter_id = quartal.id) as s1\n" +
                    "inner join djangoForest_districtforestly as district on s1.id_district_forestly_id = district.id) as s2\n" +
                    "inner join djangoForest_forestly as forestly on s2.id_forestly_id = forestly.id) as s3 where listregion.id = $id",
            null
        )
        cursor.moveToFirst()
        val vadom = Vedom(
            cursor.getString(cursor.getColumnIndex("name_forestly")),
            cursor.getString(cursor.getColumnIndex("name_district_forestly")),
            cursor.getString(cursor.getColumnIndex("quarter_name")),
            cursor.getString(cursor.getColumnIndex("soil_lot")),
            cursor.getString(cursor.getColumnIndex("date")),
            cursor.getString(cursor.getColumnIndex("sample_region")),
            cursor.getString(cursor.getColumnIndex("id_quarter_id"))
        )

        cursor.close()
        database.close()
        return vadom
    }


    @SuppressLint("Range")
    fun getlistsquare(id: Int): List<Square> {
        val database: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = database.rawQuery(
            "select * from djangoForest_sample as s1 WHERE s1.id_list_region_id = $id ",
            null
        )
        cursor.moveToFirst()
        val squareList = mutableListOf<Square>()

        for(i in 1 .. cursor.count) {
            val square = Square(
                cursor.getString(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("lenght")),
                cursor.getString(cursor.getColumnIndex("width")),
                cursor.getInt(cursor.getColumnIndex("square")).toString(),
                cursor.getString(cursor.getColumnIndex("date"))
            )
            squareList.add(square)
            cursor.moveToNext()
        }

        cursor.close()
        database.close()
        return squareList
    }
    fun UpdateSample(
        id: Int,
        date: String,
        lenght: String,
        width: String,
        sample_area: String,
    ){
        val db = this.writableDatabase
        db.execSQL(
            "update djangoForest_sample set date = '$date', square = '$sample_area', \n" +
                    "lenght = '$lenght', width = $width where id = '$id'"
        )
    }

    fun insertintolistsquare(
        date: String,
        sample_area: String,
        id_list_region_id: Int?,
        id_profile_id: Int,
        id_quarter_id: Int,
        soil_lot: String,
        lenght: String,
        square: String,
        width: String,
        mark_update: Int
    ) {
        val db = this.writableDatabase
        db.execSQL(
            "insert into djangoForest_sample (date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot,lenght,square,width,mark_update) " +
                    "values ('$date', '$sample_area', '$id_list_region_id', '$id_profile_id', '$id_quarter_id', '$soil_lot','$lenght','$square','$width','$mark_update')"
        )
    }

    @SuppressLint("Range")
    fun gethashFavoriteLes(id: Int): HashMap<Int, String> {
        var database: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = database.rawQuery(
            "select s1.id, s2.name_breed from djangoForest_forestformingbydefault as s1 inner join djangoForest_breed as s2 ON s1.id_breed_id = s2.id WHERE s1.id_profile_id = $id;",
            null
        )
        var hash = HashMap<Int, String>()
        cursor.moveToFirst()
        for (i in 1..cursor.getCount()) {
            hash.put(
                (cursor.getString(cursor.getColumnIndex("id"))).toInt(),
                (cursor.getString(cursor.getColumnIndex("name_breed")))
            )
            cursor.moveToNext()
        }
        return hash
        database.close()
    }

    @SuppressLint("Range")
    fun gethashFavoritepodLes(id: Int): HashMap<Int, String> {
        var database: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = database.rawQuery(
            "select s3.id, s4.name from djangoForest_undergrowthbydefault as s3 inner join djangoForest_undergrowth as s4 ON s3.id_undergrowth_id = s4.id WHERE s3.id_profile_id = $id;",
            null
        )
        var hash = HashMap<Int, String>()
        cursor.moveToFirst()
        for (i in 1..cursor.getCount()) {
            hash.put(
                (cursor.getString(cursor.getColumnIndex("id"))).toInt(),
                (cursor.getString(cursor.getColumnIndex("name")))
            )
            cursor.moveToNext()
        }
        return hash
        database.close()
    }

    @SuppressLint("Range")
    fun getFavoriteLes(id: Int): List<FavoriteLes> {
        val database: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = database.rawQuery(
            "select s1.id, s2.name_breed from djangoForest_forestformingbydefault as s1 inner join djangoForest_breed as s2 ON s1.id_breed_id = s2.id WHERE s1.id_profile_id = $id;",
            null
        )
        val favoriteLesList = mutableListOf<FavoriteLes>()
        cursor.moveToFirst()

        for (i in 1 .. cursor.count) {
            val favoriteLes = FavoriteLes(
                cursor.getString(cursor.getColumnIndex("name_breed")),
                cursor.getString(cursor.getColumnIndex("id"))
            )
            favoriteLesList.add(favoriteLes)
            cursor.moveToNext()
        }

        cursor.close()
        database.close()
        return favoriteLesList
    }
    @SuppressLint("Range")
    fun updatevalue(id: Int){
        val database: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = database.rawQuery("select * from djangoForest_listregion where id='$id'",null)
        cursor.moveToFirst()
        if(cursor.getInt(cursor.getColumnIndex("mark_update")) <= 1){
            database.execSQL(
                "update djangoForest_listregion set mark_update = 1 where id = $id"
            )
        }


    }
    @SuppressLint("Range")
    fun getFavoritePodles(id: Int): List<FavoritePodles> {
        val database: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = database.rawQuery(
            "select s3.id_undergrowth_id, s4.name,s3.id from djangoForest_undergrowthbydefault as s3 inner join djangoForest_undergrowth as s4 ON s3.id_undergrowth_id = s4.id WHERE s3.id_profile_id = $id;",
            null
        )
        cursor.moveToFirst()
        val favoritePodlesList = mutableListOf<FavoritePodles>()

        for (i in 1 .. cursor.count) {
            val favoritePodles = FavoritePodles(
                cursor.getString(cursor.getColumnIndex("name")),
                cursor.getString(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("id_undergrowth_id"))
            )
            favoritePodlesList.add(favoritePodles)
            cursor.moveToNext()
        }

        cursor.close()
        database.close()
        return favoritePodlesList
    }

    fun createLISTREGION(date: String, sample_region: String, id_quarter_id: Int, soil_lot: String,mark_update:Int) {
        val db = this.writableDatabase
        //Log.v(date,"")
        db.execSQL(
            "Insert into djangoForest_listregion\n" +
                    "(date, sample_region, id_quarter_id, soil_lot,mark_update,id_profile,number_region) \n" +
                    "values ('$date', '$sample_region', '$id_quarter_id', '$soil_lot','$mark_update',11,'0')"
        )
        db.close()
    }




    fun UpdateLISTREGION(
        id: Int,
        date: String,
        sample_region: String,
        id_quarter_id: Int,
        soil_lot: String
    ) {
        val db = this.writableDatabase
        //Log.v(date,"")
        db.execSQL("Update djangoForest_listregion set date = '$date', sample_region = '$sample_region', id_quarter_id = $id_quarter_id, soil_lot = '$soil_lot'  where id = $id")
        db.close()
    }

    fun getallpodles(): Cursor {
        val db = this.writableDatabase
        val cursor: Cursor =
            db.rawQuery("SELECT s1.id, s1.name FROM djangoForest_undergrowth as s1 where s1.id != 0", null)
        return cursor
        db.close()
    }

    @SuppressLint("Range")
    fun getallpodlesArray(): List<SpinerItem> {
        val db = this.writableDatabase
        val cursor: Cursor =
//            db.rawQuery("SELECT s1.id, s1.name FROM djangoForest_undergrowth as s1", null)
        db.rawQuery("SELECT s1.id, s1.name FROM djangoForest_undergrowth as s1 where s1.id != 0", null)

        val countries1 = mutableListOf<SpinerItem>()
        countries1.add(SpinerItem("не добавлять",0))
        cursor.moveToFirst()
        for (i in 1..cursor.getCount()) {
            val name=cursor.getString(cursor.getColumnIndex("name"))
            val id=cursor.getString(cursor.getColumnIndex("id")).toInt()
            countries1.add(SpinerItem(name,id))
            cursor.moveToNext()
        }
        cursor.close()
        return countries1
        db.close()
    }

    fun getallporod(): Cursor {
        val db = this.writableDatabase
        //Log.v(date,"")
        val cursor: Cursor = db.rawQuery(
            "SELECT s1.id, s1.name_breed, s1.is_foliar, s1.is_pine, s1.ShortName from djangoForest_breed as s1",
            null
        )
        return cursor
        db.close()
    }

    @SuppressLint("Range")
    fun getallporodArray(): List<SpinerItem> {
        val db = this.writableDatabase
        //Log.v(date,"")
        val cursor: Cursor = db.rawQuery(
            "SELECT s1.id, s1.name_breed, s1.is_foliar, s1.is_pine, s1.ShortName from djangoForest_breed as s1",
            null
        )
        cursor.moveToFirst()
        val countries = mutableListOf<SpinerItem>()
        countries.add(SpinerItem("не добавлять",0))
        for (i in 1..cursor.getCount()) {

            val id=cursor.getString(cursor.getColumnIndex("id")).toInt()
            val name=cursor.getString(cursor.getColumnIndex("name_breed"))
            countries.add(SpinerItem(name,id))
            cursor.moveToNext()
        }
        return countries
        db.close()
    }


    fun addlesporod(idprofile: Int, idvalue: Int) {
        val db = this.writableDatabase
        //Log.v(date,"")
        db.execSQL("insert into djangoForest_forestformingbydefault (id_profile_id,id_breed_id) Values ($idprofile, $idvalue)")
        db.close()
    }


    fun addpodlesporod(idprofile: Int, idvalue: Int) {
        val db = this.writableDatabase
        //Log.v(date,"")
        db.execSQL("insert into djangoForest_undergrowthbydefault (id_profile_id, id_undergrowth_id) Values ($idprofile, $idvalue)")
        db.close()
    }


    fun deletelesporod(id: Int) {
        val db = this.writableDatabase
        //Log.v(date,"")
        db.execSQL("delete from djangoForest_forestformingbydefault where id = $id")
        db.close()
    }

    fun deletepodlesporod(id: Int) {
        val db = this.writableDatabase
        //Log.v(date,"")
        db.execSQL("delete from djangoForest_undergrowthbydefault where id = $id" )
        db.close()
    }



    fun addReproduction(reproduction_name: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("REPRODUCTIONNAME", reproduction_name)
        db.insert("REPRODUCTION", null, values)
        db.close()
    }

//    fun CreateLesPorods(
//        to0_2: Int, from0_21To0_5: Int, from0_6To1_0: Int, from1_1to1_5: Int, from1_5: Int,
//        id_breed_id: Int?, id_sample_id: Int, id_type_of_reproduction_id: Int, avg_diameter: Float?,
//        avg_height: Float?, count_of_plants: Int, max_height: Float?, id_undergrowth_id: Int) {
//        val db = this.writableDatabase
//
//        db.execSQL(
//            """ insert into djangoForest_list (to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5,
//                    id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants,
//                    max_height, id_undergrowth_id) values($to0_2, $from0_21To0_5, $from0_6To1_0, $from1_1to1_5, $from1_5,
//                    $id_breed_id, $id_sample_id, $id_type_of_reproduction_id, $avg_diameter, $avg_height, $count_of_plants,
//                    $max_height, $id_undergrowth_id)"""
//        )
//        db.close()
//    }


    fun CreateLesPorodsV2(value: PerechetWood?,id_sample:Int){
        val db = this.writableDatabase
        val o2=value?.o2
        val o5=value?.o5
        val o6=value?.o6
        val o11=value?.o11
        val o15=value?.o15
        val maxHeight=value?.maxHeight
        val AVGHEight=value?.AVGHEight
        val AVGdiametr=value?.AVGdiametr
        var id_prob=value?.id_prob
        val id_breed=value?.id_breed
        var allwoods=value?.allwoods
        val type=value?.type

        db.execSQL(
            """ insert into djangoForest_list (to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, 
                    id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, 
                    max_height,mark_update) values(  $o2, $o5, $o6,$o11, $o15, 
                    $id_breed, $id_sample, $type, $AVGdiametr, $AVGHEight,
                    $maxHeight,2)"""
        )
        db.close()
    }


    @SuppressLint("Range")
    fun getperechetID(id_sample_id: Int, id_type_of_reproduction_id: Int, id_breed_id: Int):PerechetWood? {
        val db = this.readableDatabase
        var cursor = db.rawQuery(
            """select id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, id_breed_id,
                | id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height,
                |  count_of_plants,max_height, id_undergrowth_id  from djangoForest_list
                |   where id_sample_id = $id_sample_id and id_type_of_reproduction_id = $id_type_of_reproduction_id
                |   and id_breed_id = $id_breed_id""".trimMargin(), null
        )
        var example: PerechetWood? = PerechetWood("1", 1, 1)
        cursor.moveToFirst()
        for (i in 1..cursor.getCount()) {
            example?.o2 = cursor.getInt(cursor.getColumnIndex("to0_2"))
            example?.o5 = cursor.getInt(cursor.getColumnIndex("from0_21To0_5"))
            example?.o6 = cursor.getInt(cursor.getColumnIndex("from0_6To1_0"))
            example?.o11 = cursor.getInt(cursor.getColumnIndex("from1_1to1_5"))
            example?.o15 = cursor.getInt(cursor.getColumnIndex("from1_5"))
            example?.type =cursor.getInt(cursor.getColumnIndex("id_type_of_reproduction_id")).toInt()
            example?.AVGdiametr = cursor.getFloat(cursor.getColumnIndex("avg_diameter"))
            example?.AVGHEight = cursor.getFloat(cursor.getColumnIndex("avg_height"))
            example?.maxHeight = cursor.getFloat(cursor.getColumnIndex("max_height"))
            example?.id_prob = cursor.getInt(cursor.getColumnIndex("id"))
        }
        cursor.close()
        return example
    }
    @SuppressLint("Range")
    fun getperechetIDpodles(id_sample_id: Int,id_undegrowth: Int):PodlesokWood? {
        val db = this.readableDatabase
        var cursor = db.rawQuery(
            """select id, count_of_plants, avg_height_undergrowth  from djangoForest_list
                |   where id_sample_id = $id_sample_id  and id_undergrowth_id = $id_undegrowth""".trimMargin(), null
        )
        var example: PodlesokWood? = PodlesokWood(0, 0f,0,id_undegrowth)
        cursor.moveToFirst()
        for (i in 1..cursor.getCount()) {
            example?.id=cursor.getString(cursor.getColumnIndex("id")).toInt()
            example?.value=cursor.getString(cursor.getColumnIndex("count_of_plants")).toInt()
            example?.avgHeightpodles=cursor?.getString(cursor.getColumnIndex("avg_height_undergrowth"))?.toFloatOrNull() //fix
        }
        cursor.close()
        return example
    }




//    fun UpdateLesPorods(
//        to0_2: Int, from0_21To0_5: Int, from0_6To1_0: Int, from1_1to1_5: Int, from1_5: Int,
//        id_breed_id: Int, id_sample_id: Int, id_type_of_reproduction_id: Int, avg_diameter: Float?,
//        avg_height: Float?, count_of_plants: Int, max_height: Float?, id_undergrowth_id: Int) {
//        val db = this.writableDatabase
//
//        db.execSQL(
//            """ update djangoForest_list set to0_2 = $to0_2, from0_21To0_5 = $from0_21To0_5, from0_6To1_0 = $from0_6To1_0, from1_1to1_5 = $from1_1to1_5, from1_5 = $from1_5,
//                    avg_diameter = $from1_5, avg_height = $avg_height, count_of_plants = $count_of_plants,
//                    max_height = $max_height where id = $id_undergrowth_id"""
//        )
//        db.close()
//    }

    fun UpdateLesPorodsV2(value: PerechetWood?){
        val db = this.writableDatabase
        var o2=value?.o2
        var o5=value?.o5
        var o6=value?.o6
        var o11=value?.o11
        var o15=value?.o15
        var maxHeight=value?.maxHeight
        var AVGHEight=value?.AVGHEight
        var AVGdiametr=value?.AVGdiametr
        var id_prob=value?.id_prob

        db.execSQL(
            """ update djangoForest_list set to0_2 = $o2, from0_21To0_5 = $o5, from0_6To1_0 = $o6, from1_1to1_5 = $o11, from1_5 = $o15, 
                    avg_diameter = $AVGdiametr, avg_height = $AVGHEight, count_of_plants = 0,
                    max_height = $maxHeight, mark_update=1 where id = $id_prob"""
        )
        db.close()
    }

    fun createpodles(value: PodlesokWood?,id_sample_id: Int){
        val db = this.writableDatabase
        var count_of_plants :Int?=value?.value
        var avg_height_undergrowth :Float?=value?.avgHeightpodles
        var id_undergrowth_id :Int?=value?.idbreed_under

        db.execSQL(
            """INSERT INTO djangoForest_list( id_sample_id, count_of_plants, id_undergrowth_id, avg_height_undergrowth,mark_update )
                |VALUES ( $id_sample_id, $count_of_plants,$id_undergrowth_id,$avg_height_undergrowth,2 );""".trimMargin())

    }

    fun updatepodles(value: PodlesokWood?){
        val db = this.writableDatabase

        var count_of_plants :Int?=value?.value
        var avg_height_undergrowth :Float?=value?.avgHeightpodles
        var id :Int?=value?.id

        db.execSQL(
            """UPDATE djangoForest_list SET count_of_plants = $count_of_plants , avg_height_undergrowth = $avg_height_undergrowth,mark_update=1 WHERE id = $id""".trimMargin())

    }
    fun writephoto(photo: String,id_sample:Int,latitude:Double?,longitude:Double?,date:String){
        val db = this.writableDatabase

        db.execSQL(
            """INSERT INTO djangoForest_photopoint( photo, id_sample_id,latitude,longitude,date)
                |VALUES ( '$photo', '$id_sample','$latitude','$longitude','$date');""".trimMargin())

    }


    @SuppressLint("Recycle", "Range")
    fun getphoto(id_sample_id: Int):List<Photo>{
        val db = this.readableDatabase

        var cursor = db.rawQuery(
            """SELECT * FROM djangoForest_photopoint where id_sample_id='$id_sample_id'""".trimMargin(),null)
        cursor.moveToFirst()
        val a= mutableListOf<Photo>()
        for (i in 1..cursor.getCount()) {
            var phototemp=cursor.getString(cursor.getColumnIndex("photo"))
            val temp=GPStracker(context)
            val bmp=temp.base_to_bitmap(phototemp)
            val photosample=cursor.getInt(cursor.getColumnIndex("id_sample_id"))
            val latitude=cursor.getFloat(cursor.getColumnIndex("latitude"))
            val longitude=cursor.getFloat(cursor.getColumnIndex("longitude"))
            val date=cursor.getString(cursor.getColumnIndex("date"))

            a.add(Photo(bmp,photosample,latitude,longitude,date))
            cursor.moveToNext()
        }
        cursor.close()
        return a
    }

    @SuppressLint("Recycle", "Range")
    fun getphotoall(id_sample_id:Int):List<Photo>{
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            """SELECT * FROM djangoForest_photopoint where id_sample_id=$id_sample_id """.trimMargin(),null)
        cursor.moveToFirst()
        val a= mutableListOf<Photo>()
        for (i in 1..cursor.getCount()) {
            val phototemp=cursor.getString(cursor.getColumnIndex("photo"))
            var bmp:Bitmap?=null
            val temp=GPStracker(context)
            bmp=temp.base_to_bitmap(phototemp)
            val photosample=cursor.getInt(cursor.getColumnIndex("id_sample_id"))
            val latitude=cursor.getFloat(cursor.getColumnIndex("latitude"))
            val longitude=cursor.getFloat(cursor.getColumnIndex("longitude"))
            val date=cursor.getString(cursor.getColumnIndex("date"))
            a.add(Photo(bmp,photosample,latitude,longitude,date))
            cursor.moveToNext()
        }
        cursor.close()
        return a
    }

    fun createvedom(date: String, sample_region: String, id_quarter_id: Int,
                    soil_lot: String,mark_update:Int,id_profile:Int,number_region:String) {
        val db = this.writableDatabase
        //Log.v(date,"")
        db.execSQL(
            "Insert into djangoForest_listregion\n" +
                    "(date, sample_region, id_quarter_id, soil_lot,mark_update,id_profile,number_region) \n" +
                    "values ('$date', '$sample_region', '$id_quarter_id', '$soil_lot','$mark_update','$id_profile','$number_region')"
        )
        db.close()
    }

    fun delete_listregion(value:Int){
        val db = this.writableDatabase
        //Log.v(date,"")
        db.execSQL(
            "DELETE FROM djangoForest_listregion \n" +
                    "WHERE id = '$value';"
        )
        db.execSQL(" Insert into delte_value \n" +
                    "(name_table,id_value) \n" +
                    "values ('listregion','$value')")
        db.close()
    }

    fun delete_sample(value:Int){
        val db = this.writableDatabase
        //Log.v(date,"")
        db.execSQL(
            "DELETE FROM djangoForest_sample \n" +
                    "WHERE id = '$value';"
        )
        db.execSQL(" Insert into delte_value \n" +
                "(name_table,id_value) \n" +
                "values ('sample','$value')")
        db.close()
    }

    @SuppressLint("Range")
    fun get_delete_listregion():List<Int>{
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            """SELECT * FROM delte_value where name_table="listregion" """.trimMargin(),null)
        cursor.moveToFirst()
        val a= mutableListOf<Int>()
        for (i in 1..cursor.getCount()) {
            val photosample=cursor.getInt(cursor.getColumnIndex("id_value"))
            a.add(photosample)
            cursor.moveToNext()
        }
        cursor.close()
        return a
    }

    @SuppressLint("Range")
    fun get_delete_sample():List<Int>{
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            """SELECT * FROM delte_value where name_table="sample" """.trimMargin(),null)
        cursor.moveToFirst()
        val a= mutableListOf<Int>()
        for (i in 1..cursor.getCount()) {
            val photosample=cursor.getInt(cursor.getColumnIndex("id_value"))
            a.add(photosample)
            cursor.moveToNext()
        }
        cursor.close()
        return a
    }

    fun delete_all(){
        val db = this.readableDatabase

        db.execSQL( "DELETE   FROM delte_value  ")
        db.close()
    }




    companion object {

        // here we have defined variables for our database
        // below is variable for database name

        private val PATH ="app/src/main/res/raw/userdb.db"
        private val DATABASE_NAME = "userdb.db"


        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val TABLE_NAME = "Woods"

        // below is the variable for id column
        val ID_COL = "id"
        // below is the variable for name column

        // below is the variable for age column
        val IdSampleProba = "poroda"

        val PROBA = "proba"

        val VIEWWOOD = "viewwood"

        val VALUE02 = "value02"

        val VALUE05 = "value05"

        val VALUE06 = "value06"

        val VALUE11 = "value11"

        val VALUE15 = "value15"

        val arr = arrayOf(IdSampleProba, VIEWWOOD, VALUE02, VALUE05, VALUE06, VALUE11, VALUE15)
    }
}


