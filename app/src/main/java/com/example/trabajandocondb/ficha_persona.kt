package com.example.trabajandocondb

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class ficha_persona : AppCompatActivity() {

    lateinit var app:Global
    lateinit var nombre_vista: TextView
    lateinit var apellidos_vista:TextView
    lateinit var direccion_vista: TextView
    lateinit var tlf_vista: TextView
    lateinit var web_vista: TextView
    lateinit var foto_vista: ImageView
    lateinit var miPersona:Person
    var idPersona:Long? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ficha_persona)

        app = applicationContext as Global
        val elMensaje = getIntent()
        idPersona = intent.extras?.getString("idPersona")?.toLong()

        nombre_vista = findViewById(R.id.nombre_ficha)
        apellidos_vista = findViewById(R.id.apellido_ficha)
        direccion_vista = findViewById(R.id.direccion_ficha)
        tlf_vista = findViewById(R.id.tlf_ficha)
        web_vista = findViewById(R.id.web_ficha)
        foto_vista = findViewById(R.id.image_ficha)

        lifecycleScope.launch(){
            miPersona = app.room.personDao().damePersonaPorId(idPersona!!)
            cargarDatos()
        }
    }

    fun cargarDatos(){
        nombre_vista.text = miPersona.nombre
        apellidos_vista.text = miPersona.apellidos
        direccion_vista.text = miPersona.direccion + " " + miPersona.poblacion
        tlf_vista.text = miPersona.tlf
        web_vista.text = miPersona.web
        foto_vista.setImageBitmap(BitmapFactory.decodeFile("data/data/com.example.trabajandocondb/app_fotosUsuarioss/"+miPersona.id+".jpg"))

        //Programamos los eventos de pulsar en las vistas (botones, llamar por tlf, abrir web, etc
        tlf_vista.setOnClickListener {
            val elMensaje: Uri = Uri.parse("tel:"+miPersona.tlf)
            val elMensajero = Intent(Intent.ACTION_DIAL,elMensaje)
            startActivity(elMensajero)
        }

        web_vista.setOnClickListener {
            val elMensaje: Uri = Uri.parse("http:"+miPersona.web)
            val elMensajero = Intent(Intent.ACTION_VIEW,elMensaje)
            startActivity(elMensajero)
        }

        direccion_vista.setOnClickListener {
            val elMensaje: Uri = Uri.parse("google.navigator:q="+miPersona.direccion + ", " + miPersona.poblacion)
            val elMensajero = Intent(Intent.ACTION_VIEW,elMensaje)
            startActivity(elMensajero)
        }
    }

    /*
    * el boton borrar persona lanza una corrutina que la borra y despues abri la lista de usuarios
    * asi no aburrimos al usuario con una ficha que acaba de borrare y ya no existe
    * */
    fun borrarPersona(view: View){
        lifecycleScope.launch(){
            app.room.personDao().borrar(miPersona)
            val miMensaje = Intent(applicationContext,lista_personas::class.java)
            startActivity(miMensaje)
        }
    }

    /*
    * este boton solo lanza la ventana modificar datos
    * debemos pasarle el id del usuario a modificar
    * */
    fun modificarDatos(view:View){
        val miMensaje = Intent(this,form_editar_persona::class.java)
        miMensaje.putExtra("idPersona",idPersona.toString())
        startActivity(miMensaje)
    }
}