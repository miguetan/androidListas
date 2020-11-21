package com.example.trabajandocondb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /*
    * Boton que abre el formulario de insertar
    * */
    fun abrirForm_insertar(view: View){
        val miMensajero:Intent = Intent(this,form_nueva_persona::class.java)
        startActivity(miMensajero)
    }
    /*
    * Boton que abre el listado
    * */
    fun verListado(view: View){
        val miMensajero:Intent = Intent(this,lista_personas::class.java)
        startActivity(miMensajero)
       }

}
