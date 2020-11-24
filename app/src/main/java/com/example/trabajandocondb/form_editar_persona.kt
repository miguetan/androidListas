package com.example.trabajandocondb

import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class form_editar_persona : AppCompatActivity() {
    lateinit var app:Global
    lateinit var nombre_vista:EditText
    lateinit var apellidos_vista:EditText
    lateinit var direccion_vista:EditText
    lateinit var poblacion_vista:EditText
    lateinit var tlf_vista:EditText
    lateinit var web_vista:EditText
    lateinit var foto_vista:ImageView
    lateinit var foto_bitMap:Bitmap
    lateinit var miPersona:Person
    var idPersona:Long? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_nueva_persona)
        app = applicationContext as Global

        //debemos recuperar el id de la persona que hay que modificar que nos ha pasado la ventana que nos ha llamado
        val elMensaje = getIntent()
        idPersona = intent.extras?.getString("idPersona")?.toLong()

        nombre_vista = findViewById(R.id.editTextNombre)
        apellidos_vista = findViewById(R.id.editTextApellido)
        direccion_vista = findViewById(R.id.editTextDireccion)
        poblacion_vista = findViewById(R.id.editTextPoblacion)
        tlf_vista = findViewById(R.id.editTextTelefono)
        web_vista = findViewById(R.id.editTextWeb)

        foto_vista = findViewById(R.id.imageView_formulario_insert)

        lifecycleScope.launch {
            miPersona = app.room.personDao().damePersonaPorId(idPersona!!)
            nombre_vista.setText(miPersona.nombre)
            apellidos_vista.setText(miPersona.apellidos)
            direccion_vista.setText(miPersona.direccion)
            poblacion_vista.setText(miPersona.poblacion)
            tlf_vista.setText(miPersona.tlf)
            web_vista.setText(miPersona.web)
        }
    }

    /*
    * esto es el boton que guarda los datos
    * */
    fun guardar(view: View) {
        lifecycleScope.launch {
            miPersona.nombre = nombre_vista.text.toString()
            miPersona.apellidos = apellidos_vista.text.toString()
            miPersona.direccion = direccion_vista.text.toString()
            miPersona.poblacion = poblacion_vista.text.toString()
            miPersona.tlf = tlf_vista.text.toString()
            miPersona.web = web_vista.text.toString()
            //la funcion actualizar tiene en cuenta todos los datos de la persona
            //como sabe su id, ya sabe cual tiene q modificar
            app.room.personDao().actualizar(miPersona)
        } //fin de la coroutine
    }
}
