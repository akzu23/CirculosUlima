package pe.edu.ulima.circulos_ulima

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class ProfesorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profesoractivity)

        //declarar variables

        var profesorimagen : ImageView = findViewById(R.id.profesorimagen)
        var boton1 : Button = findViewById(R.id.comentarios)
        var boton2 : Button = findViewById(R.id.solicitudes)
        var boton3 : Button = findViewById(R.id.logoff)

        var circuloprofe : String = intent.getStringExtra("circulo")



        boton1.setOnClickListener {

            //boton de comentarios
            val intent = Intent()
            intent.setClass(this, ComentariosProfesor::class.java)
            intent.putExtra("circulo2", circuloprofe)
            Toast.makeText(this, circuloprofe, Toast.LENGTH_LONG).show()
            startActivity(intent)

        }

        boton2.setOnClickListener {
            //boton de solicitudes
            val intent = Intent()
            intent.setClass(this, Solicitudes::class.java)
            intent.putExtra("circulo2", circuloprofe)
            Toast.makeText(this, circuloprofe, Toast.LENGTH_LONG).show()
            startActivity(intent)
        }

        boton3.setOnClickListener {
            //boton de regresar
            val intent: Intent = Intent()
            intent.setClass(this, MainActivity::class.java)

            startActivity(intent)

        }

        profesorimagen.setBackgroundResource(R.drawable.prosor)
    }

}