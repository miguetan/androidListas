package com.example.trabajandocondb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Person::class],
    version = 1
)
abstract class PeopleDB:RoomDatabase() {

    abstract fun personDao(): PersonDao

}