package pe.daniel.arce.dsmpc02

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val txtDni = findViewById<EditText>(R.id.txtDni)
        val txtClave: TextView = findViewById(R.id.txtClave)
        val btnIniciar: Button = findViewById(R.id.btnIniciar)
        val btnCrear: Button = findViewById(R.id.btnCrear)
        val db = FirebaseFirestore.getInstance()

        btnIniciar.setOnClickListener {
            var dni: String = txtDni.text.toString()
            var clave: String = txtClave.text.toString()

            val userRef = db.collection("usuarios")
            userRef.whereEqualTo("dni",dni ).whereEqualTo("clave",clave)

            userRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d("Firebase", "DocumentSnapshot data: $document")
                    } else {
                        Log.d("Firebase", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("Firebase", "get failed with ", exception)
                }
        }



        btnCrear.setOnClickListener {
            this.showRegister()
        }
    }

    private fun showRegister() {
        val intentCenters = Intent(this, RegistroActivity::class.java)
        startActivity(intentCenters)
    }

    private fun showPrincipal() {
        val intentCenters = Intent(this, PrincipalActivity::class.java)
        startActivity(intentCenters)
    }
}