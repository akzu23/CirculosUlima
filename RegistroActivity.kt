package pe.edu.ulima.circulos_ulima

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import pe.edu.ulima.circulos_ulima.clases.Alumno

class RegistroActivity : AppCompatActivity() {
    //declaracion de variables

    lateinit var botonreg : Button
    lateinit var botonacep : Button
    lateinit var cuadro1 : EditText
    lateinit var cuadro2 : EditText
    lateinit var cuadro3 : EditText
    lateinit var cuadro4 : EditText
    lateinit var nombre : String
    lateinit var codigo : String
    lateinit var correo : String
    lateinit var contrasena : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro_activity)

        botonreg = findViewById(R.id.Regresar)
        botonacep = findViewById(R.id.aceptar)

        cuadro1 = findViewById(R.id.nombre)
        cuadro2 = findViewById(R.id.codigo)
        cuadro3 = findViewById(R.id.contrasena)
        cuadro4 = findViewById(R.id.correo)




        botonreg.setOnClickListener {

            //boton de regresar
            val intent: Intent = Intent()
            intent.setClass(this, MainActivity::class.java)
            startActivity(intent)
        }

        botonacep.setOnClickListener {

            nombre = cuadro1.text.toString()
            codigo = cuadro2.text.toString()
            contrasena = cuadro3.text.toString()
            correo = cuadro4.text.toString()

            if (nombre.isEmpty() || codigo.isEmpty() || correo.isEmpty() || contrasena.isEmpty()){

                Toast.makeText(this, "Llene todos los campos porfavor", Toast.LENGTH_SHORT).show()

                //analizar datos
            }else {
                registrarUsuario()
                val intent: Intent = Intent()
                intent.setClass(this, RegistrarCirculo::class.java)
                startActivity(intent)
            }
        }

    }

    private fun registrarUsuario(){

        val persona = Alumno(codigo, contrasena, correo, nombre)

        val ref = FirebaseDatabase.getInstance().getReference("Alumnos")

        ref.child(codigo).setValue(persona)

        //ingresar y registrar nuevo alumno
    }


}
