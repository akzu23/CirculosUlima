package pe.edu.ulima.circulos_ulima

import android.app.DownloadManager
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import pe.edu.ulima.circulos_ulima.clases.Nota

class ComentariosProfesor : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.comentarios_activity)

        //declarar variables

        var boton1 : Button = findViewById(R.id.volver)
        var boton2 : Button = findViewById(R.id.publicar)
        var mitexto1 : EditText = findViewById(R.id.cabecera)
        var mitexto2 : EditText = findViewById(R.id.tiempo)
        var mitexto3 : EditText = findViewById(R.id.cuerpo)


        var circuloprofesor : String = intent.getStringExtra("circulo2")

        Log.d("a", circuloprofesor)



        boton1.setOnClickListener {

            //boton de regresar

            val intent: Intent = Intent()
            intent.setClass(this, ProfesorActivity::class.java)
            startActivity(intent)
        }

        boton2.setOnClickListener {

            var titulo = mitexto1.text.toString()
            var fecha = mitexto2.text.toString()
            var cuerpo = mitexto3.text.toString()



            //para agregar una nota o mensaje a la BD Firebase

            val ref = FirebaseDatabase.getInstance().getReference("Circulos").child(circuloprofesor).child("notas")

            var agregar = Nota(cuerpo, fecha, titulo)

            ref.child(titulo).setValue(agregar)

            Toast.makeText(this, "Su nota ha sido creado exitosamente", Toast.LENGTH_SHORT).show()

            val intent: Intent = Intent()
            intent.setClass(this, ProfesorActivity::class.java)
            intent.putExtra("circulo", circuloprofesor)
            startActivity(intent)
        }
    }

}