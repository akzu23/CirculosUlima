package pe.edu.ulima.circulos_ulima

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import pe.edu.ulima.circulos_ulima.clases.Circulo

class Descripcion : AppCompatActivity() {

    //declarar variables
    lateinit var botonreg : Button
    lateinit var botonins : Button

    lateinit var milista : MutableList<Circulo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.descripcion)

        var codigo = intent.getStringExtra("codigo")

        var nombreC = intent.getStringExtra("nombreC")

        var nombre = intent.getStringExtra("nombre")

        var id = intent.getStringExtra("id")

        var query2 : Query = FirebaseDatabase.getInstance().getReference("Circulos").orderByChild("id").equalTo(id)

        milista = mutableListOf()

        query2.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("Data",dataSnapshot.toString())
                if (dataSnapshot.exists()){
                    milista.clear()
                    for (h in dataSnapshot.children) {
                        val circulo = h.getValue(Circulo::class.java)
                        milista.add(circulo!!)
                        val titulo : TextView = findViewById(R.id.titulo)
                        val titulo2 : TextView = findViewById(R.id.descripcion)
                        val titulo3 : TextView = findViewById(R.id.ubicacion)
                        titulo.setText(circulo.nombre)
                        titulo2.setText(circulo.descripcion)
                        titulo3.setText(circulo.ubicacion)
                        //para cada circulo se llama al query y se completan datos
                    }

                }
            }
        })





        botonreg = findViewById(R.id.regreso)
        botonins = findViewById(R.id.Inscripcion)



        val query : Query = FirebaseDatabase.getInstance().getReference("Alumnos").child(codigo).child("circulos").orderByChild("id").equalTo(id)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("Data",dataSnapshot.toString())
                if (dataSnapshot.exists()){
                    botonins.text = "Foro"

                }else{
                    botonins.text = "Inscribirse"
                }
            }
        })
        //se cambia el texto de el boton para el usuario

        botonreg.setOnClickListener {

            val intent: Intent = Intent()
            intent.setClass(this, MostrarCirculos::class.java)
            intent.putExtra("codigo", codigo)
            intent.putExtra("nombreC", nombreC)
            intent.putExtra("nombre", nombre)
            startActivity(intent)
        }

        //se crea 2 caminos, si esta inscrito o no

        botonins.setOnClickListener {

            if (botonins.text == "Inscribirse"){
                Toast.makeText(this, "Se ha enviado su solicitud", Toast.LENGTH_SHORT).show()

                var ref1 = FirebaseDatabase.getInstance().getReference("Circulos").child(nombreC)

                ref1.child("Solicitudes").child(nombre).setValue(codigo)

            }else {

                val intent: Intent = Intent()
                intent.setClass(this, Foro::class.java)
                intent.putExtra("codigo", codigo)
                intent.putExtra("id", id)
                intent.putExtra("nombreC", nombreC)
                intent.putExtra("nombre", nombre)
                startActivity(intent)

            }
        }

    }


}