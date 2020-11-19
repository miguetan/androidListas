package com.example.trabajandocondb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var app:PersonApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        app = applicationContext as PersonApp

        System.currentTimeMillis() //saca un Long con la fecha actual en milisegundos. Ideal para ponerle como nombre unico
  }

    fun inserta(view: View){
        lifecycleScope.launch{
            Log.d("miapp","hola mundo")
            app.room.personDao().insert(Person("aa",23))
        }
    }

    fun traeTodo(view: View){
        lifecycleScope.launch{
            var todo:List<Person> = app.room.personDao().getAll()
            for(i in 0..todo.size.minus(1)){
                Log.d("miapp","Nombre " + todo[i].id + " " + todo[i].name + " " + todo[i].age )
        }
       }
    }

    fun verLista(view:View){
        val elMensajero: Intent = Intent(this,lista_personas::class.java)
        startActivity(elMensajero)
    }

}