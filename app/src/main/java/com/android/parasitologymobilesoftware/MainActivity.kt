package com.android.parasitologymobilesoftware

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val reference = database.getReference("teste")

        reference.setValue("teste do firebase no aplicativo :D")

    }

    fun onButtonLogInClick(view: View) {
        val editTextEmail = findViewById<EditText>(R.id.editTextLogInEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextLogInPassword)
        firebaseAuth.signInWithEmailAndPassword(editTextEmail.text.toString(), editTextPassword.text.toString())
            .addOnCompleteListener(this) { Toast.makeText(this, "Sucesso! Logando com sua conta...", Toast.LENGTH_SHORT).show() }
            .addOnFailureListener(this) {Toast.makeText(this, "Falhou... Essa conta não existe.", Toast.LENGTH_SHORT).show() }
    }

    fun onButtonLogInRegisterClick(view: View) {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }
}
