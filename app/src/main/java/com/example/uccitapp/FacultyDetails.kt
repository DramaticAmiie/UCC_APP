package com.example.uccitapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.Shape
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class FacultyDetails: AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_directory_details)

        //Gets value sent from Directory activity
        val index: String? = this.intent.getStringExtra("listIndex")

        //Creates objects for each activity element
        val name: TextView = findViewById(R.id.textStaff)
        val email: TextView = findViewById(R.id.textEmail)
        val phoneNumber: TextView = findViewById(R.id.textPhoneNumber)
        val photo: ImageView = findViewById(R.id.imgStaff)
        val imgFaculty: ImageView = findViewById(R.id.imgFaculty)
            imgFaculty.setBackgroundResource(R.drawable.texture)

        imgFaculty.alpha= 0.15f

        //Assigns value to elements according to retrieved index
        when(index){
            "0" -> {
                name.text = "Christina Miller"
                email.text = "cmiller@ucc.edu.jm"
                phoneNumber.text = "8764869386"
                photo.setImageResource(R.drawable.christina_miller)
            }
            "1" -> {
                name.text = "Fiona Erhunse"
                email.text = "ferhunse@ucc.edu.jm"
                phoneNumber.text = "8763076908"
                photo.setImageResource(R.drawable.fiona_erhunse)
            }
            "2" -> {
                name.text = "Tahiti Spears"
                email.text = "tspears@ucc.edu.jm"
                phoneNumber.text = "8762111672"
                photo.setImageResource(R.drawable.tahiti_spears)
            }
            "3" -> {
                name.text = "Kristen Hall"
                email.text = "khall@ucc.edu.jm"
                phoneNumber.text = "8765157977"
                photo.setImageResource(R.drawable.kristen_hall)
            }
            "4" -> {
                name.text = "Juan Luis"
                email.text = "jluis@ucc.edu.jm'"
                phoneNumber.text = "8764202002"
                photo.setImageResource(R.drawable.juan_luis)
            }
            "5" -> {
                name.text = "Terrence Underwood"
                email.text = "tunderwood@ucc.edu.jm"
                phoneNumber.text = "8766790530"
                photo.setImageResource(R.drawable.terrence_underwood)
            }
            "6" -> {
                name.text = "Vince Fleming"
                email.text = "vfleming@ucc.edu.jm"
                phoneNumber.text = "8769255523"
                photo.setImageResource(R.drawable.vince_fleming)
            }
        }

        //Launches email client when email address is tapped
        email.setOnClickListener{
            val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email.toString(), null))
            intent.putExtra(Intent.EXTRA_EMAIL, email.toString())
            startActivity(intent)
        }

        phoneNumber.setOnClickListener{
            val number = phoneNumber.text.toString()
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
            startActivity(intent)
        }
    }


}