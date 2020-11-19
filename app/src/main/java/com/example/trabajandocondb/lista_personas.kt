package com.example.trabajandocondb

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class lista_personas : AppCompatActivity() {
    lateinit var miPersona_Recycler: RecyclerView
    lateinit var app:PersonApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista_personas)
        app = applicationContext as PersonApp
        miPersona_Recycler = findViewById(R.id.recycler_vista)

        miPersona_Recycler.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch(){
            var todo:List<Person> = app.room.personDao().getAll()
            cargarLista(todo)

        }

    }

    fun cargarLista(todo:List<Person>){
        miPersona_Recycler.adapter = elAdaptador(todo,this)
    }

    class elAdaptador(var personas:List<Person>,var context:Context):RecyclerView.Adapter<elViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): elViewHolder {
            return elViewHolder(LayoutInflater.from(context).inflate(R.layout.lista_personas_fila,parent,false))
        }

        override fun onBindViewHolder(holder: elViewHolder, position: Int) {

            val miPersona = personas[position]
            holder.enlazar(miPersona,context)
            holder.itemView.setOnClickListener {
                // TODO: 19-Nov-20 Hacer intent para que habra la ficha de person
                Toast.makeText(context,"Me hicieron Click " + personas[position].id,Toast.LENGTH_LONG).show()
            }
        }

        override fun getItemCount(): Int {
            return personas.size
        }

    }

    class elViewHolder(view: View):RecyclerView.ViewHolder(view){
        val elId: TextView = view.findViewById(R.id.id)
        val elNombre: TextView = view.findViewById(R.id.nombre)
        val laEdad: TextView = view.findViewById(R.id.edad)

        fun enlazar(laPersona:Person,context:Context){
            elId.text = laPersona.id.toString()
            elNombre.text = laPersona.name.toString()
            laEdad.text = laPersona.age.toString()
        }
    }
}

