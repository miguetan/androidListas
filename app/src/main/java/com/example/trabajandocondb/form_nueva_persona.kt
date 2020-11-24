package com.example.trabajandocondb

import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class form_nueva_persona : AppCompatActivity() {

    //creamos todas las variables de clase
    lateinit var app:Global
    lateinit var nombre_vista:EditText
    lateinit var apellidos_vista:EditText
    lateinit var direccion_vista:EditText
    lateinit var poblacion_vista:EditText
    lateinit var tlf_vista:EditText
    lateinit var web_vista:EditText
    lateinit var foto_vista:ImageView
    lateinit var foto_bitMap:Bitmap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_nueva_persona)
        app = applicationContext as Global

        //traemos las vistas a Kotlin
        nombre_vista = findViewById(R.id.editTextNombre)
        apellidos_vista = findViewById(R.id.editTextApellido)
        direccion_vista = findViewById(R.id.editTextDireccion)
        poblacion_vista = findViewById(R.id.editTextPoblacion)
        tlf_vista = findViewById(R.id.editTextTelefono)
        web_vista = findViewById(R.id.editTextWeb)
        foto_vista = findViewById(R.id.imageView_formulario_insert)
        }

    fun guardar(view: View) {
        lifecycleScope.launch {
            //creamos un objeto persona para meterlo en la tabla. Lo pongo en varias lineas para facilitar la lectura
            val miPersona:Person = Person(
                                            nombre_vista.text.toString(),
                                            apellidos_vista.text.toString(),
                                            direccion_vista.text.toString(),
                                            poblacion_vista.text.toString(),
                                            tlf_vista.text.toString(),
                                            web_vista.text.toString()
                                            )
            val id:Long = app.room.personDao().insert(miPersona) //ejecutamos laorden de insercion
            //aqui ya tengo garantias de que la fila se metio en la tabla, ahora guardo la foto
            val laCarpetaDeLaApp = ContextWrapper(applicationContext)
            val laSubcarpetaDeLasFotos = laCarpetaDeLaApp.getDir("fotosUsuarioss", MODE_PRIVATE)
            val miFoto = File(laSubcarpetaDeLasFotos, id.toString() + ".jpg")
            val elFlujoDeDatos = FileOutputStream(miFoto)
            foto_bitMap.compress(Bitmap.CompressFormat.JPEG,100,elFlujoDeDatos)
            elFlujoDeDatos.flush()
            elFlujoDeDatos.close()
            Toast.makeText(applicationContext,"Creado",Toast.LENGTH_LONG).show()
            nombre_vista.visibility = View.INVISIBLE
        } //fin de la coroutine
    }

    /*
    * esta funcion solo abre la camara
    * */
    fun sacarFoto(view:View){
            val elMensajero = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(elMensajero,10)
       }

    /*
    * esta funcion recupera la foto y la deja en memoria hasta que halla que usarla
    * Se guardara en disco cuando la base de datos termine de insertar los datos (Linea 58)
    * */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==10){
            foto_bitMap = data?.extras?.get("data") as Bitmap
            foto_vista.setImageBitmap(foto_bitMap)
        }
    }
}
