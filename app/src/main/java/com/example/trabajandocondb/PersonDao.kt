package com.example.trabajandocondb

import androidx.room.*

@Dao
interface PersonDao {

    //aqui definimos TOOOODASSS  las operacions que haremos con la tabla Person
    @Insert
    suspend fun insert(person:Person):Long

    @Delete
    suspend fun borrar(person:Person)

    @Update
    suspend fun actualizar(person:Person)

    @Query("SELECT * FROM Person")
    suspend fun getAll():List<Person>

    @Query("SELECT * FROM Person WHERE id=:id")
    suspend fun damePersonaPorId(id:Long):Person

}