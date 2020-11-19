package com.example.trabajandocondb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Person(
    val name:String,
    val age:Int
){
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}