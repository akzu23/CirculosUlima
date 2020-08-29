package pe.edu.ulima.circulos_ulima

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.ListAdapter
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import pe.edu.ulima.circulos_ulima.Adapters.SolicitudAdapter
import pe.edu.ulima.circulos_ulima.clases.Circulo

class Solicitudes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.solicitudesactivity)

        //declarar variables

        var milista : ListView = findViewById(R.id.listasolicitud)

        var circulo = intent.getStringExtra("circulo2")

        var miboton : Button = findViewById(R.id.regresosoli)

        var lista : List<String>

        lista = mutableListOf()

        var ref2 = FirebaseDatabase.getInstance().getReference("Circulos").child(circulo).child("Solicitudes")

        ref2.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){
                    lista.clear()
                    Log.d("que es", p0.toString())
                    for (h in p0.children){
                        val misolicitud = h.getValue()
                        lista.add(misolicitud!!.toString())
                    }
                    val adapter2 = ListAdapter(
                        this@Solicitudes,
                        R.layout.solicitudesactivity,
                        lista.component1()
                    )
                    milista.adapter = adapter2
                    //guardar y mostrar las solicitudes pendientes
                }
            }

        })

        miboton.setOnClickListener {

            val intent = Intent()
            intent.setClass(this, ProfesorActivity::class.java)
            intent.putExtra("circulo", circulo)
            startActivity(intent)

        }
    }

}