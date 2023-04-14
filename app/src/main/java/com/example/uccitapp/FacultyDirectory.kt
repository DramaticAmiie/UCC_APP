package com.example.uccitapp

import android.content.Intent
import android.os.Bundle
import android.telecom.Call
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class FacultyDirectory : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_directory)

        val imgFaculty: ImageView = findViewById(R.id.imgFaculty)
        imgFaculty.setBackgroundResource(R.drawable.texture)

        imgFaculty.alpha= 0.1f
        //Reads staff list from items in strings.xml file
        val staffList: Array<String> = resources.getStringArray(R.array.staff)
        //Creates adapter that sets list type and list item
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, staffList)

        val lvFaculty: ListView = findViewById(R.id.lvDirectory)
        //ListView control populated with list items
        lvFaculty.adapter = adapter

        //Defines action when list item is clicked
        lvFaculty.setOnItemClickListener { parent, view, position, id ->
            //Displays a message showing list item index
            //Toast.makeText(this, "Clicked item : "+position, Toast.LENGTH_SHORT).show()
            //Specifies which activity should be launched
            val intent = Intent(this, FacultyDetails::class.java);
            //Stores index number to be shared with Details activity
            intent.putExtra("listIndex", position.toString());
            //Starts activity
            this.startActivity(intent);
        }

        val homeBtn : ImageButton = findViewById(R.id.btnHome)
        homeBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val staffBtn : ImageButton = findViewById(R.id.btnStaff)
        staffBtn.setOnClickListener{
            /*val intent = Intent(this, StaffDetailsActivity::class.java)
            startActivity(intent)*/
        }

        val admissionBtn : ImageButton = findViewById(R.id.btnAdmission)
        admissionBtn.setOnClickListener{
             val intent = Intent(this, AdmissionActivity::class.java)
             startActivity(intent)
        }

        val courseBtn : ImageButton = findViewById(R.id.btnCourse)
        courseBtn.setOnClickListener{
            val intent = Intent(this, CourseActivity::class.java)
            startActivity(intent)
        }
    }

}
