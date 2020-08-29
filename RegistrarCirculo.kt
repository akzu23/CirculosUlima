package pe.edu.ulima.circulos_ulima

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.principal_activity.*
import pe.edu.ulima.circulos_ulima.Adapters.CirculosAdapter
import pe.edu.ulima.circulos_ulima.clases.Circulo

class RegistrarCirculo : AppCompatActivity() {
    lateinit var textito : EditText
    lateinit var boton : Button
    lateinit var nombre : String
    lateinit var listView : ListView

    lateinit var listacirculos : MutableList<Circulo>

    lateinit var ref : DatabaseReference

    //declarar variables

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.principal_activity)

        ref = FirebaseDatabase.getInstance().getReference("Circulos")

        listacirculos = mutableListOf()

        listView = findViewById(R.id.lista)

        textito = findViewById(R.id.textito)

        boton = findViewById(R.id.button)

        var spinner : Spinner = findViewById(R.id.spinner)

        var adapter : ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,
            R.array.opciones, android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.setAdapter(adapter)

        boton.setOnClickListener {
            guardarCirculo()
        }

        ref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){
                    listacirculos.clear()
                    for (h in p0.children){
                        val circulo = h.getValue(Circulo::class.java)
                        listacirculos.add(circulo!!)
                    }
                    //guardar los circulos en listacirculos

                    val adapter = CirculosAdapter(
                        applicationContext,
                        R.layout.mostrarcirculos,
                        listacirculos
                    )
                    listView.adapter = adapter
                }
            }

        })

    }


    private fun guardarCirculo(){
        nombre = textito.text.toString().trim()

        if (nombre.isEmpty()){
            textito.error = "Porfavor ingresa un circulo"
            return
        }




        val llavecircle = ref.push().key

        if (spinner.selectedItem.toString() == "Estudios"){
            val circle = Circulo(llavecircle.toString(), nombre, "Estudios")

            ref.child(llavecircle!!).setValue(circle).addOnCompleteListener {
                Toast.makeText(applicationContext, "Se guardo el circulo correctamente", Toast.LENGTH_SHORT)
            }
        }else if (spinner.selectedItem.toString() == "Deportes"){
            val circle = Circulo(llavecircle.toString(), nombre, "Deportes")

            ref.child(llavecircle!!).setValue(circle).addOnCompleteListener {
                Toast.makeText(applicationContext, "Se guardo el circulo correctamente", Toast.LENGTH_SHORT)
            }
        }else if (spinner.selectedItem.toString() == "Artes"){
            val circle = Circulo(llavecircle.toString(), nombre, "Artes")

            ref.child(llavecircle!!).setValue(circle).addOnCompleteListener {
                Toast.makeText(applicationContext, "Se guardo el circulo correctamente", Toast.LENGTH_SHORT)
            }
        }

        //saber de que tipo es el circulo introducido


    }
}