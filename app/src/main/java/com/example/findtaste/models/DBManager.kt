package com.example.findtaste.models

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBManager (
    val context: Context?,
    val name: String?,
    val factory: SQLiteDatabase.CursorFactory?,
    val version: Int
) : SQLiteOpenHelper(context, name, factory, version){

    override fun onCreate(db: SQLiteDatabase?){
        val favs = """
            CREATE TABLE favorites(
                id INTEGER PRIMARY KEY NOT NULL,
                commerce TEXT NOT NULL,
                address TEXT,
                description TEXT,
                latitude REAL,
                longitude REAL,
                category TEXT,
                telephone TEXT,
                photo TEXT
            );
        """.trimIndent()

        db?.let {
            it.execSQL(favs)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun addCommerce(commerce: Commerce){
        val db = writableDatabase
        val sql = """
           INSERT INTO favorites(commerce, address, description, category, latitude, longitude, telephone, photo) VALUES(
                '${commerce.commerce}',
                '${commerce.address}',
                '${commerce.description}',
                '${commerce.lat}',
                '${commerce.lng}',
                '${commerce.category}',
                '${commerce.telephone}',
                '${commerce.photo}'
           ) 
        """.trimIndent()

        db.execSQL(sql)
        db.close()
    }

    fun removeFav(commerce: Commerce){
        val db = writableDatabase

        db.execSQL("DELETE FROM favorites WHERE id=${commerce.id}")
        db.close()
    }

}