package com.example.uccitapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AdmissionActivity : AppCompatActivity(){

    val dbTable = "Admission"
    var id = 0

    private val programmeUrl : String = "https://ucc.edu.jm/programmes/tm/bsc-in-information-technology"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admissions)

        val imgFaculty: ImageView = findViewById(R.id.imgFaculty)
        imgFaculty.setBackgroundResource(R.drawable.texture)

        imgFaculty.alpha= 0.1f
        val img : ImageView = findViewById(R.id.imgAdmit)
        val heading: TextView = findViewById(R.id.textHeadingAdmission)
        val body: TextView = findViewById(R.id.textAdmissionInfo)
        val urlLink: TextView = findViewById(R.id.textAdmissionUrl)
        val req = "Individuals should possess a minimum of five (5) subjects at the GCE" +
                " or CSEC level (including the mandatory English Language and Mathematics) " +
                "at grades A, B, C or 1, 2, 3 respectively. A CSEC pass at level 3 must have" +
                " been obtained since 1998."
        val department = "Information Technology"

        heading.text = department
        body.text = req
        urlLink.text = "Click to learn more"

        img.setImageResource(R.drawable.it)

        urlLink.setOnClickListener {
            val intent = Intent(this, Web::class.java)
            intent.putExtra("PROGRAMME", programmeUrl)
            startActivity(intent)
        }

        val homeBtn: ImageButton = findViewById(R.id.btnHome)
        homeBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val staffBtn: ImageButton = findViewById(R.id.btnStaff)
        staffBtn.setOnClickListener {
            val intent = Intent(this, FacultyDirectory::class.java)
            startActivity(intent)
        }

        val admissionBtn: ImageButton = findViewById(R.id.btnAdmission)
        admissionBtn.setOnClickListener {
            /*  val intent = Intent(this, AdmissionActivity::class.java)
         startActivity(intent)*/
        }

        val courseBtn: ImageButton = findViewById(R.id.btnCourse)
        courseBtn.setOnClickListener {
            val intent = Intent(this, CourseActivity::class.java)
            startActivity(intent)
        }
    }

}


