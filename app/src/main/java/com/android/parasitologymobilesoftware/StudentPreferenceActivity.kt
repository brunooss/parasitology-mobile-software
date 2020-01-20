package com.android.parasitologymobilesoftware

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.database.FirebaseDatabase

class StudentPreferenceActivity : AppCompatActivity() {

    private var studentPreference: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_preference)

        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val reference = database.getReference("teste")

        reference.setValue("teste do firebase no aplicativo :D")

        val constraintLayoutStudentFirst = findViewById<ConstraintLayout>(R.id.constraintLayoutStudentFirst)
        val constraintLayoutStudentSecond = findViewById<ConstraintLayout>(R.id.constraintLayoutStudentSecond)

        constraintLayoutStudentFirst.setOnClickListener { view ->
            if(studentPreference == 2) {
                constraintLayoutStudentSecond.setBackgroundResource(R.drawable.custom_buttom_yellow_background)
                studentPreference = 1
            }
            view.setBackgroundResource(R.drawable.custom_button_yellow_background_pressed)
        }
        constraintLayoutStudentSecond.setOnClickListener { view ->
            if(studentPreference == 1) {
                constraintLayoutStudentFirst.setBackgroundResource(R.drawable.custom_buttom_yellow_background)
                studentPreference = 2
            }
            view.setBackgroundResource(R.drawable.custom_button_yellow_background_pressed)
        }
    }

    public fun onButtonStudentPreferenceClick(view: View) {
        startActivity(Intent(this, HomeActivity::class.java))
    }
}
