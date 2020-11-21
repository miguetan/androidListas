package com.example.trabajandocondb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Person(
    var nombre:String,
    var apellidos:String,
    var direccion:String,
    var poblacion:String,
    var tlf:String,
    var web:String,
){
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0
}