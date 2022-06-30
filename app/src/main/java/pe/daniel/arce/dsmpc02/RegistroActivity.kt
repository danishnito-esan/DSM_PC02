package pe.daniel.arce.dsmpc02

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import pe.daniel.arce.dsmpc02.model.UsuarioModel
import java.util.*

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val txtDniRegistro: EditText = findViewById(R.id.txtDniRegistro)
        val txtNombreCompleto: EditText = findViewById(R.id.txtNombreCompleto)
        val txtClaveNueva: EditText = findViewById(R.id.txtClaveNueva)
        val txtVerificaClave: EditText = findViewById(R.id.txtVerificaClave)
        val btnRegistrar: Button = findViewById(R.id.btnRegistrar)
        val db = FirebaseFirestore.getInstance()


        btnRegistrar.setOnClickListener {
            var clave = txtClaveNueva.text.toString()
            var verificaClave = txtVerificaClave.text.toString()
            if (clave == verificaClave) {
                var dni = txtDniRegistro.text.toString()
                var nombreCompleto = txtNombreCompleto.text.toString()

                val newUser = UsuarioModel(dni, nombreCompleto, clave)
                val newID: String = UUID.randomUUID().toString()

                db.collection("usuarios")
                    .document(newID)
                    .set(newUser)
                    .addOnSuccessListener {
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            "Se registró correctamente",
                            Snackbar.LENGTH_LONG
                        ).show()
                        showLogin()
                    }.addOnFailureListener {
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            "Error al registrar el usuario....",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
            } else {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Las claves no coinciden",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun showLogin() {
        val intentCenters = Intent(this, LoginActivity::class.java)
        startActivity(intentCenters)
    }
}