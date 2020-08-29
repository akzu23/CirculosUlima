package pe.edu.ulima.circulos_ulima

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import pe.edu.ulima.circulos_ulima.Adapters.ForoAdapter
import pe.edu.ulima.circulos_ulima.clases.Nota

class DescripcionActivityForo : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forodescripcion)

        //declarar variables

        var titulo = intent.getStringExtra("Titulo")

        var nombreC = intent.getStringExtra("nombreC")

        var codigo = intent.getStringExtra("codigo")

        var nombre = intent.getStringExtra("nombre")

        var fecha : TextView = findViewById(R.id.fechadescripcion)

        var title : TextView = findViewById(R.id.titulodescripcion)

        var botonreg : Button = findViewById(R.id.botonvolver)

        botonreg.setOnClickListener {

            //boton de regreso
            val intent: Intent = Intent()
            intent.setClass(this, Foro::class.java)
            intent.putExtra("nombreC", nombreC)
            intent.putExtra("nombre", nombre)
            intent.putExtra("codigo", codigo)
            startActivity(intent)

        }

        var milista : MutableList<Nota>

        milista = mutableListOf()

        var texto : TextView = findViewById(R.id.descripcion2)

        var ref = FirebaseDatabase.getInstance().getReference("Circulos").child(nombreC).child("notas").orderByChild("titulo").equalTo(titulo)

        ref.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){
                    milista.clear()
                    Log.d("Data", p0.toString())
                    for (h in p0.children){
                        val nota = h.getValue(Nota::class.java)
                        milista.add(nota!!)
                        texto.text = nota.descripcion
                        fecha.text = nota.fecha
                        title.text = nota.titulo
                        //configurar la nota con los parametos ingresados
                    }

                }
            }


        })
    }

}