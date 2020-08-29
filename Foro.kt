package pe.edu.ulima.circulos_ulima

import android.app.DownloadManager
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.*
import pe.edu.ulima.circulos_ulima.Adapters.CirculosPrincipalAdapter
import pe.edu.ulima.circulos_ulima.Adapters.ForoAdapter
import pe.edu.ulima.circulos_ulima.clases.Circulo
import pe.edu.ulima.circulos_ulima.clases.Nota

class Foro : AppCompatActivity() {

    lateinit var listanotas : MutableList<Nota>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.foroactivity)
        //declarar variables

        var milista : ListView = findViewById(R.id.listaforo)

        var micodigo = intent.getStringExtra("codigo")

        var nombreC = intent.getStringExtra("nombreC")

        var nombre = intent.getStringExtra("nombre")

        listanotas = mutableListOf()

        var botonreg : Button = findViewById(R.id.forovolver)

        var id = intent.getStringExtra("id")

        botonreg.setOnClickListener {
            val intent: Intent = Intent()
            intent.putExtra("id", id)
            intent.putExtra("codigo", micodigo)
            intent.putExtra("nombreC", nombreC)
            intent.putExtra("nombre", nombre)
            intent.setClass(this, MostrarCirculos::class.java)

            //boton de regresar
            startActivity(intent)
        }


           var ref = FirebaseDatabase.getInstance().getReference("Circulos").child(nombreC).child("notas")

            ref.addValueEventListener(object : ValueEventListener{

                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0!!.exists()){
                        Log.d("Data", p0.toString())
                        listanotas.clear()
                        for (h in p0.children){
                            val nota = h.getValue(Nota::class.java)
                            listanotas.add(nota!!)
                        }

                        val adapter = ForoAdapter(
                            this@Foro,
                            R.layout.foroadapter,
                            listanotas
                        )
                        milista.adapter = adapter
                    }
                }


            })
        //agregar a una lista todas las notas


        milista.setOnItemClickListener { parent, view, position, id ->
            val intent: Intent = Intent()
            intent.setClass(this, DescripcionActivityForo::class.java)
            intent.putExtra("Titulo", listanotas[position].titulo)
            intent.putExtra("nombreC", nombreC)
            intent.putExtra("codigo", micodigo)
            intent.putExtra("nombre", nombre)
            startActivity(intent)

            //saber que objeto de la lista se selecciono
        }

    }

}