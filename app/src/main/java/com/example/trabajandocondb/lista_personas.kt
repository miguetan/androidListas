package com.example.trabajandocondb

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import java.io.File

class lista_personas : AppCompatActivity() {
    lateinit var miPersona_Recycler: RecyclerView
    lateinit var app:Global

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista_personas)
        app = applicationContext as Global
        miPersona_Recycler = findViewById(R.id.recycler_vista)

        miPersona_Recycler.layoutManager = LinearLayoutManager(this)

        //lanzamos la lista como corrutina para que acceda a la base de datos a su ritmo sin estresar al activity
        lifecycleScope.launch(){
            var laLista:List<Person> = app.room.personDao().getAll() //esto trae todos los registros
            miPersona_Recycler.adapter = elAdaptador(laLista,applicationContext) //al adaptador le ponemos applicationcontext pq dentro de la clase no es visible la variable THIS
        }

    }


    class elAdaptador(var personas:List<Person>,var context:Context):RecyclerView.Adapter<elViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): elViewHolder {
            return elViewHolder(LayoutInflater.from(context).inflate(R.layout.fila_listado,parent,false))
        }

        override fun onBindViewHolder(holder: elViewHolder, position: Int) {
            val miPersona = personas[position]
            holder.enlazar(miPersona,context)
            //hacemos que la fila sea pulsable y le asignamos el intent que abre la ficha de la persona
            holder.itemView.setOnClickListener {
                val miMensajero: Intent = Intent(context,ficha_persona::class.java)
                miMensajero.putExtra("idPersona",miPersona.id.toString())
                miMensajero.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) //esto es necesario para abrir activities fuera del contexto
                context.startActivity(miMensajero)
            }
        }

        override fun getItemCount(): Int = personas.size
    }

    class elViewHolder(view: View):RecyclerView.ViewHolder(view){
        val laFoto:ImageView = view.findViewById(R.id.imageView_fila)
        val nombre:TextView = view.findViewById(R.id.nombre_fila)
        val apellidos:TextView = view.findViewById(R.id.apellidos_fila)

        fun enlazar(laPersona:Person,context:Context){
            nombre.text = laPersona.nombre
            apellidos.text = laPersona.apellidos
            laFoto.setImageBitmap(BitmapFactory.decodeFile("data/data/com.example.trabajandocondb/app_fotosUsuarioss/"+laPersona.id.toString()+".jpg"))
        }
    }
}

