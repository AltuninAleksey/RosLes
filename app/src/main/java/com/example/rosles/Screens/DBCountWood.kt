package com.example.rosles

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.rosles.Models.*
import com.example.rosles.ResponceClass.PerechetWood
import com.example.rosles.ResponceClass.PodlesokWood
import com.example.rosles.ResponceClass.ProbaWoodSimple
import com.example.roslesdef.Models.SpinerItem
import java.util.*

class DBCountWood(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {

    }

    fun updatevalue(id: Int){
        val database: SQLiteDatabase = this.writableDatabase
        database.execSQL(
            "update djangoForest_listregion set mark_update = 1 where id = $id"
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
                cursor.getString(cursor.getColumnIndex("mark_update"))
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
                cursor.getString(cursor.getColumnIndex("id")),
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
    fun getVedombyIDObject(id: Int?){
        val database: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = database.rawQuery(
            "select listregion.id, s3.name_forestly, s3.name_district_forestly, s3.quarter_name, s3.id_quarter_id, s3.soil_lot, s3.sample_region, s3.date  \n" +
                    "from (((djangoForest_listregion as listregion inner join djangoForest_quarter as quartal on listregion.id_quarter_id = quartal.id) as s1\n" +
                    "inner join djangoForest_districtforestly as district on s1.id_district_forestly_id = district.id) as s2\n" +
                    "inner join djangoForest_forestly as forestly on s2.id_forestly_id = forestly.id) as s3 where listregion.id = $id",
            null
        )
        cursor.moveToFirst()

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
                cursor.getString(cursor.getColumnIndex("square")),
                cursor.getString(cursor.getColumnIndex("date"))
            )
            squareList.add(square)
            cursor.moveToNext()
        }

        cursor.close()
        database.close()
        return squareList
    }

    fun insertintolistsquare(
        date: String,
        sample_area: String,
        id_list_region_id: Int,
        id_profile_id: Int,
        id_quarter_id: Int,
        soil_lot: String,
        lenght: String,
        square: String,
        width: String
    ) {
        val db = this.writableDatabase
        db.execSQL(
            "insert into djangoForest_sample (date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot,lenght,square,width) " +
                    "values ('$date', '$sample_area', '$id_list_region_id', '$id_profile_id', '$id_quarter_id', '$soil_lot','$lenght','$square','$width')"
        )
    }

    @SuppressLint("Range")
    fun gethashFavoriteLes(id: Int): HashMap<Int, String> {
        val database: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = database.rawQuery(
            "select s1.id, s2.name_breed from djangoForest_forestformingbydefault as s1 inner join djangoForest_breed as s2 ON s1.id_breed_id = s2.id WHERE s1.id_profile_id = $id;",
            null
        )
        cursor.moveToFirst()
        val hash = HashMap<Int, String>()
        for (i in 1..cursor.getCount()) {
            hash.put(
                (cursor.getString(cursor.getColumnIndex("id")).toInt()),
                (cursor.getString(cursor.getColumnIndex("name_breed")))
            )
            cursor.moveToNext()
        }

        cursor.close()
        database.close()
        return hash
    }

    @SuppressLint("Range")
    fun gethashFavoritepodLes(id: Int): HashMap<Int, String> {
        val database: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = database.rawQuery(
            "select s3.id, s4.name from djangoForest_undergrowthbydefault as s3 inner join djangoForest_undergrowth as s4 ON s3.id_undergrowth_id = s4.id WHERE s3.id_profile_id = $id;",
            null
        )
        val hash = HashMap<Int, String>()
        cursor.moveToFirst()
        for (i in 1..cursor.getCount()) {
            hash.put(
                (cursor.getString(cursor.getColumnIndex("id"))).toInt(),
                (cursor.getString(cursor.getColumnIndex("name")))
            )
            cursor.moveToNext()
        }

        cursor.close()
        database.close()
        return hash
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


    fun createvedom(date: String, sample_region: String, id_quarter_id: Int, soil_lot: String) {
        val db = this.writableDatabase
        //Log.v(date,"")
        db.execSQL(
            "Insert into djangoForest_listregion\n" +
                    "(date, sample_region, id_quarter_id, soil_lot) \n" +
                    "values ('$date', '$sample_region', $id_quarter_id, '$soil_lot')"
        )
        db.close()
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

   /* fun getallpodles(): Cursor {
        val db = this.writableDatabase
        val cursor: Cursor =
            db.rawQuery("SELECT s1.id, s1.name FROM djangoForest_undergrowth as s1", null)


        db.close()
        return cursor
    }*/

    @SuppressLint("Range")
    fun getallpodlesArray(): List<SpinerItem> {
        val db = this.writableDatabase
        val cursor: Cursor =
            db.rawQuery("SELECT s1.id, s1.name FROM djangoForest_undergrowth as s1", null)
        val countries1 = mutableListOf<SpinerItem>()
        countries1.add(SpinerItem("не добавлять",0))
        cursor.moveToFirst()
        for (i in 1..cursor.count) {
            countries1.add(
                SpinerItem(cursor.getString(cursor.getColumnIndex("name")),
                cursor.getString(cursor.getColumnIndex("id")).toInt())
            )
            cursor.moveToNext()
        }

        cursor.close()
        db.close()
        return countries1
    }

    fun getallporod(): Cursor {
        val db = this.writableDatabase
        //Log.v(date,"")
        val cursor: Cursor = db.rawQuery(
            "SELECT s1.id, s1.name_breed, s1.is_foliar, s1.is_pine, s1.ShortName from djangoForest_breed as s1",
            null
        )
        db.close()
        return cursor
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
        for (i in 1..cursor.count) {
            countries.add(
                SpinerItem(
                    cursor.getString(cursor.getColumnIndex("name_breed")),
                    cursor.getString(cursor.getColumnIndex("id")).toInt())
            )
            cursor.moveToNext()
        }

        cursor.close()
        db.close()
        return countries
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


    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
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
        var o2=value?.o2
        var o5=value?.o5
        var o6=value?.o6
        var o11=value?.o11
        var o15=value?.o15
        var maxHeight=value?.maxHeight
        var AVGHEight=value?.AVGHEight
        var AVGdiametr=value?.AVGdiametr
        var id_prob=value?.id_prob
        var id_breed=value?.id_breed
        var allwoods=value?.allwoods
        var type=value?.type

        db.execSQL(
            """ insert into djangoForest_list (to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, 
                    id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, 
                    max_height) values(  $o2, $o5, $o6,$o11, $o15, 
                    $id_breed, $id_sample, $type, $AVGdiametr, $AVGHEight,
                    $maxHeight)"""
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
            example?.o2 = cursor.getString(cursor.getColumnIndex("to0_2")).toInt()
            example?.o5 = cursor.getString(cursor.getColumnIndex("from0_21To0_5")).toInt()
            example?.o6 = cursor.getString(cursor.getColumnIndex("from0_6To1_0")).toInt()
            example?.o11 = cursor.getString(cursor.getColumnIndex("from1_1to1_5")).toInt()
            example?.o15 = cursor.getString(cursor.getColumnIndex("from1_5")).toInt()
            example?.type =cursor.getString(cursor.getColumnIndex("id_type_of_reproduction_id")).toInt()
            example?.AVGdiametr = cursor.getString(cursor.getColumnIndex("avg_diameter")).toFloat()
            example?.AVGHEight = cursor.getString(cursor.getColumnIndex("avg_height")).toFloat()
            example?.maxHeight = cursor.getString(cursor.getColumnIndex("max_height")).toFloat()
            example?.id_prob = cursor.getString(cursor.getColumnIndex("id")).toInt()
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
            example?.avgHeightpodles=cursor.getString(cursor.getColumnIndex("avg_height_undergrowth")).toFloatOrNull()
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
                    max_height = $maxHeight where id = $id_prob"""
        )
        db.close()
    }

    fun createpodles(value: PodlesokWood?,id_sample_id: Int){
        val db = this.writableDatabase
        var count_of_plants :Int?=value?.value
        var avg_height_undergrowth :Float?=value?.avgHeightpodles
        var id_undergrowth_id :Int?=value?.idbreed_under

        db.execSQL(
            """INSERT INTO djangoForest_list( id_sample_id, count_of_plants, id_undergrowth_id, avg_height_undergrowth )
                |VALUES ( $id_sample_id, $count_of_plants,$id_undergrowth_id,$avg_height_undergrowth );""".trimMargin())

    }

    fun updatepodles(value: PodlesokWood?){
        val db = this.writableDatabase

        var count_of_plants :Int?=value?.value
        var avg_height_undergrowth :Float?=value?.avgHeightpodles
        var id :Int?=value?.id

        db.execSQL(
            """UPDATE djangoForest_list SET count_of_plants = $count_of_plants , avg_height_undergrowth = $avg_height_undergrowth WHERE id = $id""".trimMargin())

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


