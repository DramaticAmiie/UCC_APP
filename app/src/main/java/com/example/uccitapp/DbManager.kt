package com.example.uccitapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.util.Log
import android.widget.Toast

class DbManager {

    val tag ="DB Results"
    //database name
    var dbName = "ucc_it.db"
    //table names
    var courseTable = "Courses"

    //columns for course
    var courseColID = "id"
    var courseColCode = "code"
    var courseColName = "name"
    var courseColCredits = "credits"
    var courseColPrerequisites = "prerequisites"
    var courseColDescription = "description"

    //database version
    var dbVersion = 1

    //CREATE TABLE IF NOT EXISTS UCC
    val sqlCreateCourseTable = "CREATE TABLE IF NOT EXISTS $courseTable ($courseColID " +
            "INTEGER PRIMARY KEY,$courseColCode TEXT, $courseColName TEXT, $courseColCredits " +
            " INTEGER, $courseColPrerequisites TEXT, $courseColDescription TEXT) "
              //   //

    var sqlDB: SQLiteDatabase? = null
    var qryDB: SQLiteDatabase? = null
      constructor(context: Context)
      {
          var db = DatabaseHelperUCC(context)
          sqlDB = db.writableDatabase
          qryDB = db.readableDatabase
      }
    inner class DatabaseHelperUCC : SQLiteOpenHelper {
        var context: Context? = null

        constructor(context: Context) : super(context,dbName, null,dbVersion){
            this.context = context
        }

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCreateCourseTable)
           // Toast.makeText(this.context, "database created...", Toast.LENGTH_SHORT).show()

        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS $courseTable")
            onCreate(db)
        }

        /*  fun insert(db: SQLiteDatabase?){
              db!!.execSQL(checkTables)
          }*/
    }


    fun insertData(name: String, code: String, credits: Int, prerequisite: String, description: String): Long {
        val values = ContentValues()
        values.put(courseColName, name)
        values.put(courseColCode, code)
        values.put(courseColCredits, credits)
        values.put(courseColPrerequisites, prerequisite)
        values.put(courseColDescription, description)
        val db = sqlDB
        val id = db!!.insert(courseTable, null, values)
        //db.close()
        return id
    }

    fun insertData(courses: List<Course>): List<Long> {
        val db = sqlDB
        val result = mutableListOf<Long>()
        for (course in courses) {
            val values = ContentValues()
            values.put(courseColName, course.courseName)
            values.put(courseColCode, course.courseCode)
            values.put(courseColCredits, course.courseCredits)
            values.put(courseColPrerequisites, course.coursePrerequisites)
            values.put(courseColDescription, course.courseDescription)
            result.add(db!!.insert(courseTable, null, values))
        }
        //db.close()
        return result
    }


    fun query(
        projection: Array<String>,
        selection: String,
        selectionArgs: Array<String>,
        sorOrder: String
    ): Cursor {
        val qb = SQLiteQueryBuilder()
        qb.tables = courseTable
        return qb.query(sqlDB, projection, selection, selectionArgs, null, null, sorOrder)
    }

}






