package pe.edu.ulima.circulos_ulima

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.*
import com.google.firebase.database.*
import pe.edu.ulima.circulos_ulima.Adapters.CirculosPrincipalAdapter
import pe.edu.ulima.circulos_ulima.clases.Circulo

class MostrarCirculos : AppCompatActivity() {
    //declarar variables

    lateinit var textito : EditText
    lateinit var boton : Button
    lateinit var listView : ListView

    lateinit var micodigo : String

    lateinit var listacirculos : MutableList<Circulo>
    lateinit var listacirculosA : MutableList<Circulo>
    lateinit var listacirculosB : MutableList<Circulo>
    lateinit var ref : DatabaseReference
    lateinit var ref2 : DatabaseReference

    var control = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mostrarcirculos)

        micodigo = intent.getStringExtra("codigo")

        var cerrarsesion : Button = findViewById(R.id.cerrarsesion)

        var boton : Button = findViewById(R.id.buscar)



        cerrarsesion.setOnClickListener {
            //boton de regresar
            val intent: Intent = Intent()
            intent.setClass(this, MainActivity::class.java)
            startActivity(intent)

        }


        listacirculos = mutableListOf()
        listacirculosA= mutableListOf()
        listacirculosB = mutableListOf()

        listView = findViewById(R.id.listaCirculo)

        Log.d("codigo", micodigo)

        var query : Query = FirebaseDatabase.getInstance().getReference("Alumnos").child(micodigo.toString()).child("circulos").orderByChild("id")

        var username : String = intent.getStringExtra("nombre")
        var nombre : TextView = findViewById(R.id.bienvenido)

        nombre.setText("Bienvenido ${username}")

        ref = FirebaseDatabase.getInstance().getReference("Circulos")
        ref2 = FirebaseDatabase.getInstance().getReference("Circulos")

        query.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){
                    listacirculosA.clear()
                    for (h in p0.children){
                        val circulo = h.getValue(Circulo::class.java)
                        listacirculosA.add(circulo!!)
                        //guardar los circulos que perteneces y mosttrarlos

                    }
                }
            }
        })

        ref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){
                    listacirculos.clear()
                    for (h in p0.children){
                        val circulo = h.getValue(Circulo::class.java)
                        listacirculosA.forEachIndexed { i, value ->
                            if (circulo!!.id == value.id)
                            {
                                listacirculos.add(circulo!!)

                            }else{
                            }
                        }
                    }
                    val adapter = CirculosPrincipalAdapter(
                        this@MostrarCirculos,
                        R.layout.circulosadapter,
                        listacirculos
                    )
                    listView.adapter = adapter
                }
            }

        })



        boton.setOnClickListener {

            ref2.addValueEventListener(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }


                //mostrar todos los circulos
                override fun onDataChange(p0: DataSnapshot) {
                    if (p0!!.exists()){
                        listacirculosB.clear()
                        for (h in p0.children){
                            val circulo = h.getValue(Circulo::class.java)
                            listacirculosB.add(circulo!!)
                        }
                        val adapter2 = CirculosPrincipalAdapter(
                            this@MostrarCirculos,
                            R.layout.circulosadapter,
                            listacirculosB
                        )
                        listView.adapter = adapter2
                    }
                }

            })

            control = 1

        }

        var seleccion = 0
        listView.setOnItemClickListener{ parent, view, position, id ->

            if (control == 0) {

                //si no se cambio el adapter

                val intent: Intent = Intent()
                intent.setClass(this, Descripcion::class.java)
                intent.putExtra("id", listacirculos[position].id)
                intent.putExtra("codigo", micodigo)
                intent.putExtra("nombreC", listacirculos[position].nombre)
                intent.putExtra("nombre", username)
                startActivity(intent)
            }else{

                //si se presiono el boton de buscar
                val intent: Intent = Intent()
                intent.setClass(this, Descripcion::class.java)
                intent.putExtra("id", listacirculosB[position].id)
                intent.putExtra("codigo", micodigo)
                intent.putExtra("nombreC", listacirculosB[position].nombre)
                intent.putExtra("nombre", username)
                startActivity(intent)
            }
        }


    }
}