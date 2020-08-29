package pe.edu.ulima.circulos_ulima

import com.google.firebase.database.FirebaseDatabase
import android.app.Application


class Persistencia : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}