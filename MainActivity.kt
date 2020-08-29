package pe.edu.ulima.circulos_ulima

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.firebase.database.*
import pe.edu.ulima.circulos_ulima.clases.Profesor
import pe.edu.ulima.circulos_ulima.clases.Alumno

class MainActivity : AppCompatActivity() {

    lateinit var boton : Button
    lateinit var codigo : EditText
    lateinit var Contrasena : EditText
    lateinit var boton2 : Button
    lateinit var ref : DatabaseReference
    lateinit var query : Query
    lateinit var queryP : Query
    var control = false


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val checkBox : CheckBox = findViewById(R.id.checkBox)
        val imagen : ImageView = findViewById(R.id.imagen)

        ref  = FirebaseDatabase.getInstance().getReference("Alumnos")
        boton = findViewById(R.id.iniciar)
        codigo = findViewById(R.id.usuario)
        Contrasena = findViewById(R.id.password)
        var circuloprofesor : String

        boton2 = findViewById(R.id.registrarse)

        imagen.setBackgroundResource(R.drawable.logo3)

        boton.setOnClickListener {

            //declarar variables
            val casilla1 = codigo.text.toString()
            val casilla2 = Contrasena.text.toString()
            val QueryReal : Query

            query = FirebaseDatabase.getInstance().getReference("Alumnos").orderByChild("codigo").equalTo(casilla1)
            queryP = FirebaseDatabase.getInstance().getReference("Profesor").orderByChild("codigo").equalTo(casilla1)

            if (checkBox.isChecked == true){
                QueryReal = queryP
                QueryReal.keepSynced(true)
            }else{
                QueryReal = query
                QueryReal.keepSynced(true)
            }

            QueryReal.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(dataSnapshot: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("DAta",dataSnapshot.toString())
                    if (dataSnapshot.exists()){
                        Log.d("a",dataSnapshot.toString())
                        for (h in dataSnapshot.children){
                            Log.d("b",dataSnapshot.toString())
                            val busquedaP = h.getValue(Profesor::class.java)
                            val busqueda = h.getValue(Alumno::class.java)

                            //registrar profesores y alumnos
                            if (checkBox.isChecked == true){
                                if (busquedaP!!.codigo == casilla1 && busquedaP!!.contrasena==casilla2){
                                    control = true
                                }else{

                                }

                                if (control == true) {
                                    //si ingresa un profesor
                                    control = false
                                    val intent = Intent()
                                    intent.setClass(this@MainActivity, ProfesorActivity::class.java)
                                    val parametros = Bundle()
                                    parametros.putString("a",busquedaP.circulo)
                                    intent.putExtras(parametros)
                                    intent.putExtra("Codigo", busquedaP.codigo)
                                    intent.putExtra("circulo", busquedaP.circulo)
                                    Toast.makeText(this@MainActivity, busquedaP.circulo, Toast.LENGTH_LONG).show()
                                    startActivity(intent)
                                }else{
                                    Log.d("Error", "Error")
                                }
                            }else{

                                if (busqueda!!.codigo == casilla1 && busqueda!!.contrasena==casilla2){
                                    control = true
                                }else{

                                }

                                if (control == true) {
                                    //ingresa un alumno
                                    Log.d("boolean3",control.toString())
                                    control = false
                                    val intent: Intent = Intent()
                                    intent.setClass(this@MainActivity, MostrarCirculos::class.java)
                                    val parametros = Bundle()
                                    parametros.putString("nombre",busqueda.nombre)
                                    intent.putExtras(parametros)
                                    intent.putExtra("codigo", busqueda.codigo)

                                    startActivity(intent)

                                }else{
                                    Log.d("Error", "Error")
                                }
                            }
                        }
                    }
                }
            })



        }

        boton2.setOnClickListener {
            val intent: Intent = Intent()
            intent.setClass(this, RegistroActivity::class.java)

            startActivity(intent)
        }
    }
}