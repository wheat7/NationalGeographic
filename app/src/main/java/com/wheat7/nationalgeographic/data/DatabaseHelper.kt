package com.wheat7.nationalgeographic.data

import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by wheat7 on 2017/9/24.
 * https://github.com/wheat7
 */
class DatabaseHelper : SQLiteOpenHelper {

    constructor(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : super(context, name, factory, version)
    constructor(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int, errorHandler: DatabaseErrorHandler?) : super(context, name, factory, version, errorHandler)


    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(CREATE_COLLECTS)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("drop table if exists collects")
        onCreate(p0)
    }

    companion object {
        val CREATE_COLLECTS = "create table Collects (" +
                "uid integer primary key autoincrement, " +
                "id text," +
                "albumid text," +
                "title text," +
                "content text," +
                "url text," +
                "size text," +
                "addtime text," +
                "author text," +
                "thumb text," +
                "encoded text," +
                "weburl text," +
                "type text," +
                "yourshotlink text," +
                "copyright text," +
                "pmd5 text," +
                "sort text)"

    }
}