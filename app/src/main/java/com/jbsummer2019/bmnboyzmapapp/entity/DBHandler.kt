package com.jbsummer2019.bmnboyzmapapp.entity

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.service.autofill.UserData

class DBHandler(context: Context) : SQLiteOpenHelper(context, DBName, null, DBVersion) {
    companion object{
        val DBName = "PlacesDB"
        val DBVersion = 1
        val tableName = "PlacesDB"
        val _id = "id"
        val placePosition1 = "Position1"
        val placePosition2 = "Position2"
        val placeTitle = "Title"
        val placeText = "Text"
        val placeImageID = "ImageID"
        val placeIconImageID = "IconImageID"
        val placeLike = "Like_or_not"
    }
    var sqlObj: SQLiteDatabase = this.writableDatabase

    override fun onCreate(p0: SQLiteDatabase?) {
        var sql1 = "CREATE TABLE IF NOT EXISTS $tableName ( $_id INTEGER PRIMARY KEY, $placePosition1 DOUBLE, $placePosition2 DOUBLE, $placeTitle TEXT, $placeText TEXT, $placeImageID INTEGER, $placeIconImageID INTEGER, $placeLike BOOL);"
        p0!!.execSQL(sql1)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("Drop table IF EXISTS $tableName")
        onCreate(p0)
    }

    fun addPlace(values: ContentValues) = sqlObj.insert(tableName, "", values)

    fun updatePlace(values: ContentValues, id: Int) = sqlObj.update(tableName, values, "id = ?", arrayOf(id.toString()))

    fun listPlacesByLike(key : String) : ArrayList<MarkerEntity> {
        var arraylist = ArrayList<MarkerEntity>()
        var sqlQB = SQLiteQueryBuilder()
        sqlQB.tables = tableName
        var cols = arrayOf(_id, placePosition1, placePosition2, placeTitle, placeText, placeImageID, placeIconImageID, placeLike)
        var selectArgs = arrayOf(key)
        var cursor = sqlQB.query(sqlObj, cols,"$placeLike like ?", selectArgs,null,null, placeTitle)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(_id))
                val pp1 = cursor.getDouble(cursor.getColumnIndex(placePosition1))
                val pp2 = cursor.getDouble(cursor.getColumnIndex(placePosition2))
                val pT = cursor.getString(cursor.getColumnIndex(placeTitle))
                val pTe = cursor.getString(cursor.getColumnIndex(placeText))
                val pI = cursor.getInt(cursor.getColumnIndex(placeImageID))
                val pII = cursor.getInt(cursor.getColumnIndex(placeIconImageID))
                val pL = cursor.getString(cursor.getColumnIndex(placeLike)).toBoolean()

                arraylist.add(MarkerEntity(id, pp1, pp2,pT,pTe,pI,pII,pL))

            } while (cursor.moveToNext())
        }
        return arraylist
    }

    fun listPlacesByTitle(key : String) : ArrayList<MarkerEntity> {
        var arraylist = ArrayList<MarkerEntity>()
        var sqlQB = SQLiteQueryBuilder()
        sqlQB.tables = tableName
        var cols = arrayOf(_id, placePosition1, placePosition2, placeTitle, placeText, placeImageID, placeIconImageID, placeLike)
        var selectArgs = arrayOf(key)
        var cursor = sqlQB.query(sqlObj, cols,"$placeTitle like ?", selectArgs,null,null, placeTitle)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(_id))
                val pp1 = cursor.getDouble(cursor.getColumnIndex(placePosition1))
                val pp2 = cursor.getDouble(cursor.getColumnIndex(placePosition2))
                val pT = cursor.getString(cursor.getColumnIndex(placeTitle))
                val pTe = cursor.getString(cursor.getColumnIndex(placeText))
                val pI = cursor.getInt(cursor.getColumnIndex(placeImageID))
                val pII = cursor.getInt(cursor.getColumnIndex(placeIconImageID))
                val pL = cursor.getString(cursor.getColumnIndex(placeLike)).toBoolean()

                arraylist.add(MarkerEntity(id, pp1, pp2,pT,pTe,pI,pII,pL))

            } while (cursor.moveToNext())
        }
        return arraylist
    }
}
