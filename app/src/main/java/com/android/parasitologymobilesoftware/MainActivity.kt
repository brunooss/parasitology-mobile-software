package com.android.parasitologymobilesoftware

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val reference = database.getReference("teste")

        reference.setValue("teste do firebase no aplicativo :D")
    }
}
