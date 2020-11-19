package com.example.trabajandocondb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PersonDao {

    @Insert
    suspend fun insert(person:Person)

    @Query("SELECT * FROM Person")
    suspend fun getAll():List<Person>

}