package com.example.trabajandocondb

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

class Global:Application() {
        lateinit var room:PeopleDB
    override fun onCreate() {
        super.onCreate()
           room = Room.databaseBuilder(this, PeopleDB::class.java, "bbddPersonas").build()

       }


}