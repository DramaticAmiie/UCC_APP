package com.example.uccitapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.graphics.Color
import android.icu.text.CaseMap
import android.os.Bundle
import android.provider.ContactsContract
import android.util.EventLogTags
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import java.util.ArrayList


class CourseActivity : AppCompatActivity(){

    private val tag ="Results"
    var listCourses = ArrayList<Course>()
    val courseTable = "Courses"
    var id = 0

   // @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)

        load("%")


        val homeBtn : ImageButton = findViewById(R.id.btnHome)
        homeBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val staffBtn : ImageButton = findViewById(R.id.btnStaff)
        staffBtn.setOnClickListener{
            val intent = Intent(this, FacultyDirectory::class.java)
            startActivity(intent)
        }

        val admissionBtn : ImageButton = findViewById(R.id.btnAdmission)
        admissionBtn.setOnClickListener{
             val intent = Intent(this, AdmissionActivity::class.java)
             startActivity(intent)
        }

        val courseBtn : ImageButton = findViewById(R.id.btnCourse)
        courseBtn.setOnClickListener{
            /*val intent = Intent(this, CourseActivity::class.java)
            startActivity(intent)*/
        }

    }
  @SuppressLint("Range")
  private fun load(title:String)
  {
      val coursesToInsert = listOf(
          Course(
              "ITT01", "Computer Information Systems",
              3, "None", "This course " +
                      "typically covers the fundamental concepts and principles of information " +
                      "systems, including hardware and software components, database systems, " +
                      "networking technologies, and information security."
          ),
          Course(
              "ITT02", "Programming Techniques", 3,
              "Computer Information Systems", "This course covers the" +
                      " fundamental programming concepts and techniques used in software " +
                      "development. It introduces students to programming languages, data structures," +
                      " algorithms, debugging, testing, and object-oriented programming."
          ),
          Course(
              "ITT03", "Database Management Systems", 3,
              "Programming Techniques", "This course teaches students how to design, create, " +
                      "and manage databases. The course covers topics such as database architecture," +
                      " data modeling, database normalization, database security, and query optimization. "
          ),
          Course(
              "ITT04", "Internet Authoring I", 3,
              "Programming Techniques", "This course focuses " +
                      "on the fundamental principles and techniques of web design and development."
          ),
          Course(
              "ITT05", "Introduction to Cryptography", 3,
              "None", "This course provides an introduction to the basic " +
                      "concepts and techniques of cryptography, including symmetric-key cryptography, " +
                      "public-key cryptography, digital signatures, and key exchange protocols."
          ),
          Course(
              "ITT06", "Introduction to Psychology", 3,
              "Introduction to Psychology", "Introduction to Psychology is a " +
                      "foundational course that provides an overview of the scientific study of human " +
                      "behavior and mental processes. This course typically covers a broad range of " +
                      "topics such as research methods, biological and cognitive processes, perception, " +
                      "learning and memory, development, personality, social psychology, and " +
                      "various applied areas of psychology."
          ),
          Course(
              "ITT07", "Human Computer Interaction & Design", 3,
              "Introduction to Psychology", "Human-Computer Interaction (HCI) " +
                      "and Design is a course that focuses on the design and evaluation of interactive " +
                      "computing systems for human use. It covers topics such as user-centered design, " +
                      "usability engineering, user experience design, interaction design, and evaluation " +
                      "methods."
          ),
          Course(
              "ITT08",
              "Object Oriented Programming Design using C++",
              3,
              "None",
              "Object Oriented Programming (OOP) Design using C++ is a course that " +
                      "teaches the fundamental principles and concepts of OOP and how to implement them using " +
                      "the C++ programming language. The course covers topics such as encapsulation, " +
                      "inheritance, polymorphism, abstraction, and data structures."
          ),
          Course(
              "ITT09", "Building Applications with C#", 3,
              "Object Oriented Programming Design using C++", "Building " +
                      "Applications with C# is a course that teaches the fundamentals of developing " +
                      "applications using the C# programming language. This course covers topics such " +
                      "as object-oriented programming, data types, control structures, arrays, and " +
                      "collections."
          ),
          Course(
              "ITT10", "Mobile Application & Development", 3,
              "None", "Mobile Application Development is a course that teaches students how to " +
                      "develop applications for mobile devices such as smartphones and tablets. The course covers " +
                      "topics such as mobile programming languages, mobile operating systems, user interface " +
                      "design, mobile app testing, and deployment."
          )
      )

      var dbHelper = DbManager(this)
      val db = dbHelper.qryDB
      val countCursor: Cursor = db!!.rawQuery("SELECT COUNT(*) FROM ${dbHelper.courseTable}", null)
      countCursor.moveToFirst()
      val cnt = countCursor.getInt(0)
      countCursor.close()


      Log.d(tag, "There are $cnt rows in the course table.")

      if(cnt == 0) {
          val result = dbHelper.insertData(coursesToInsert)

          // Check the result
          if (result.isNotEmpty()) {
              Log.d(tag, "Inserted ${result.size} courses into the database.")
          } else {
              Log.e(tag, "Failed to insert courses into the database.")
          }
      }

      val projections = arrayOf("code", "name", "credits", "prerequisites", "description")
      val selectionArgs = arrayOf(title)
      //sort by title
      val cursor = dbHelper.query(projections, "code like ?", selectionArgs, "code")
      listCourses.clear()
      //ascending
      if (cursor.moveToFirst()) {
          do {
              val code = cursor.getString(cursor.getColumnIndex("code"))
              val name = cursor.getString(cursor.getColumnIndex("name"))
              val credits = cursor.getInt(cursor.getColumnIndex("credits"))
              val prerequisites = cursor.getString(cursor.getColumnIndex("prerequisites"))
              val description = cursor.getString(cursor.getColumnIndex("description"))

              listCourses.add(Course(code, name, credits, prerequisites, description))

          } while (cursor.moveToNext())
      }

  //adapter
    var myCourseAdapter = MyCourseAdapter(this, listCourses)
    //set adapter
    var lvCourse: ListView = findViewById(R.id.lvCourse)
    lvCourse.adapter = myCourseAdapter

  }
inner class MyCourseAdapter : BaseAdapter {
        var listCourseAdapter = ArrayList<Course>()
        var context: Context? = null

        constructor(context: Context, listCourseAdapter: ArrayList<Course>) : super() {
            this.listCourseAdapter = listCourseAdapter
            this.context = context
        }

        override fun getItem(position: Int): Any {
            return listCourseAdapter[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View  {
            //inflate layout row
            var myView = layoutInflater.inflate(R.layout.row, null)
            val myCourse = listCourseAdapter[position]

            val bg : CardView = myView.findViewById(R.id.rowCard)
            bg.setBackgroundColor(Color.WHITE)
           // bg.alpha= 1f

            val hdCode : TextView = myView.findViewById(R.id.hdCode)
            val hdCredits : TextView = myView.findViewById(R.id.hdCredits)
            val hdPrereq : TextView = myView.findViewById(R.id.hdPrerequisites)
            val hdDescript : TextView = myView.findViewById(R.id.hdDescription)

            hdCode.text = "Code"
            hdCredits.text = "Credits"
            hdDescript.text ="Description"
            hdPrereq.text = "Prerequisites"

            val txtCourseName : TextView = myView.findViewById(R.id.textCourseName)
            val txtCourseCode : TextView = myView.findViewById(R.id.textCourseCode)
            val txtCourseCredits : TextView = myView.findViewById(R.id.textCourseCredits)
            val txtCoursePrerequisites : TextView = myView.findViewById(R.id.textCoursePrerequisites)
            val txtCourseDescription : TextView = myView.findViewById(R.id.textCourseDescription)



            txtCourseName.text = myCourse.courseName
            txtCourseCode.text = myCourse.courseCode
            txtCourseCredits.text =  myCourse.courseCredits.toString()
            txtCoursePrerequisites.text = myCourse.coursePrerequisites
            txtCourseDescription.text = myCourse.courseDescription

            return myView

        }

        override fun getCount(): Int {
            return listCourseAdapter.size
        }
    }
    /*
    inner class MyCourseAdapter : BaseAdapter {
        var listCourseAdapter = ArrayList<Course>()
        var context: Context? = null

        constructor(context: Context, listCourseAdapter: ArrayList<Course>) : super() {
            this.listCourseAdapter = listCourseAdapter
            this.context = context
        }



        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        //inflate layout row
            var myView = layoutInflater.inflate(R.layout.row, null)
            val myCourse= listCourseAdapter[position]

            val name: TextView = findViewById(R.id.textCourseName)
            val code: TextView = findViewById(R.id.textCourseCode)
            val credits: TextView = findViewById(R.id.textCourseCredits)
            val prerequisite: TextView = findViewById(R.id.textCoursePrerequisites)
            val description: TextView = findViewById(R.id.textCourseDescription)

            name.text = "myCourse.courseName"
            code.text = myCourse.courseCode
            credits.text = myCourse.courseCredits.toString()
            prerequisite.text = myCourse.coursePrerequisites
            description.text = myCourse.courseDescription

        return myView
        }

        override fun getCount(): Int {
            return listCourseAdapter.size
        }

        override fun getItem(position: Int): Any {
            return listCourseAdapter[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }
    }*/
/*
    @SuppressLint("Range")
    private fun loadQuery(title: String) {
        var dbManager = DbManager(this)
        val projections = arrayOf("id", "name", "code", "credits", "prerequisite", "description")
        val selectionArgs = arrayOf(title)
        //sort by title
        val cursor = dbManager.query(projections, "Title like ?", selectionArgs, "Title")
        listCourses.clear()
        //ascending
        if (cursor.moveToFirst()) {
            do {

               // val id = cursor.getInt(cursor.getColumnIndex("id"))
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val code= cursor.getString(cursor.getColumnIndex("code"))
                val credits= cursor.getInt(cursor.getColumnIndex("credits"))
                val prerequisite= cursor.getString(cursor.getColumnIndex("prerequisites"))
                val description= cursor.getString(cursor.getColumnIndex("description"))


                listCourses.add(Course(name, code, credits, prerequisite, description))

            } while (cursor.moveToNext())
        }

        //adapter
        var myCourseAdapter = MyCourseAdapter(this, listCourses)
        //set adapter
        var lvCourse: ListView = findViewById(R.id.lvCourse)
        lvCourse.adapter = myCourseAdapter

    }*/






/*
fun addCourse(){
    val dbManager = DbManager(this)

    val values1 = ContentValues()
    val values2 = ContentValues()
    val values3 = ContentValues()
    val values4 = ContentValues()
    val values5 = ContentValues()
    val values6 = ContentValues()
    val values7 = ContentValues()
    val values8 = ContentValues()
    val values9 = ContentValues()
    val values10 = ContentValues()

    values1.put("Code", "ITT01")
    values1.put("Name", "Computer Information Systems")
    values1.put("Credits", 3)
    values1.put("Prerequisites", "NA")
    values1.put("Description", "This course typically covers the fundamental " +
            "concepts and principles of information systems, including hardware " +
            "and software components, database systems, networking technologies, " +
            "and information security. Students learn how to design, develop, " +
            "and manage information systems that support business processes and decision-making.")

    values2.put("Code", "ITT02")
    values2.put("Name", "Programming Techniques")
    values2.put("Credits", 3)
    values2.put("Prerequisites", "Computer Information Systems")
    values2.put("Description", "This course covers the fundamental programming concepts " +
            "and techniques used in software development. It introduces students to programming " +
            "languages, data structures, algorithms, debugging, testing, and object-oriented " +
            "programming. Students will learn how to write efficient and maintainable code using" +
            " best practices and industry-standard tools.")

    values3.put("Code", "ITT03")
    values3.put("Name", "Database Management Systems")
    values3.put("Credits", 3)
    values3.put("Prerequisites", "Programming Techniques")
    values3.put("Description", "This course teaches students how to design, create, " +
            "and manage databases. The course covers topics such as database architecture," +
            " data modeling, database normalization, database security, and query optimization. " +
            "Students will also learn about different types of database systems, such as " +
            "relational, NoSQL, and object-oriented databases.")

    values4.put("Code", "ITT04")
    values4.put("Name", "Internet Authoring I")
    values4.put("Credits", 3)
    values4.put("Prerequisites", "Programming Techniques")
    values4.put("Description", "This course focuses on the fundamental principles " +
            "and techniques of web design and development. Students learn how to " +
            "create static web pages using HTML, CSS, and JavaScript, as well as how " +
            "to publish and manage websites using FTP and content management systems (CMS).")

    values5.put("Code", "ITT05")
    values5.put("Name", "Introduction to Cryptography")
    values5.put("Credits", 3)
    values5.put("Prerequisites", "NA")
    values5.put("Description", "This course provides an introduction to the basic concepts " +
            "and techniques of cryptography, including symmetric-key cryptography, public-key " +
            "cryptography, digital signatures, and key exchange protocols. Students will learn" +
            " how to use cryptographic algorithms to protect data confidentiality, integrity, " +
            "and authenticity, as well as how to evaluate and analyze the security of " +
            "cryptographic systems. ")

    values6.put("Code", "ITT06")
    values6.put("Name", "Introduction to Psychology")
    values6.put("Credits", 3)
    values6.put("Prerequisites", "NA")
    values6.put("Description", "Introduction to Psychology is a foundational course that provides" +
            " an overview of the scientific study of human behavior and mental processes. This " +
            "course typically covers a broad range of topics such as research methods, biological " +
            "and cognitive processes, perception, learning and memory, development, personality, " +
            "abnormal psychology, social psychology, and various applied areas of psychology. Through" +
            " this course, students will gain a basic understanding of the theoretical and empirical " +
            "principles of psychology, as well as an appreciation for the complexity of human behavior" +
            " and the diversity of psychological perspectives.")

    values7.put("Code", "ITT07")
    values7.put("Name", "Human Computer Interaction & Design")
    values7.put("Credits", 3)
    values7.put("Prerequisites", "Introduction to Psychology")
    values7.put("Description", "Human-Computer Interaction (HCI) and Design is a course that focuses " +
            "on the design and evaluation of interactive computing systems for human use. It covers " +
            "topics such as user-centered design, usability engineering, user experience design, " +
            "interaction design, and evaluation methods. The course emphasizes the importance of " +
            "understanding user needs and behavior in order to design effective and efficient " +
            "interfaces that meet their requirements. Students will learn about various design " +
            "principles, techniques, and tools used in HCI and design, as well as the theoretical and " +
            "practical aspects of the field. ")

    values8.put("Code", `ITT08`)
    values8.put("Name", "Object Oriented Programming Design using C++")
    values8.put("Credits", 3)
    values8.put("Prerequisites", "NA")
    values8.put("Description", "Object Oriented Programming (OOP) Design using C++ is a course that " +
            "teaches the fundamental principles and concepts of OOP and how to implement them using " +
            "the C++ programming language. The course covers topics such as encapsulation, inheritance," +
            " polymorphism, abstraction, and data structures. Students will learn how to design, " +
            "implement and test software systems using OOP principles and C++ programming language.")

    values9.put("Code", "ITT09")
    values9.put("Name", "Building Applications with C#")
    values9.put("Credits", 3)
    values9.put("Prerequisites", "Object Oriented Programming Design using C++")
    values9.put("Description", "Building Applications with C# is a course that teaches the fundamentals" +
            " of developing applications using the C# programming language. This course covers topics such " +
            "as object-oriented programming, data types, control structures, arrays, and collections. " +
            "Additionally, students will learn about graphical user interface (GUI) design using Windows " +
            "Forms, as well as how to work with databases using SQL Server and Entity Framework.")

    values10.put("Code", "ITT10")
    values10.put("Name", "Mobile Application & Development")
    values10.put("Credits", 3)
    values10.put("Prerequisites", "NA")
    values10.put("Description", "Mobile Application Development is a course that teaches students how to " +
            "develop applications for mobile devices such as smartphones and tablets. The course covers " +
            "topics such as mobile programming languages, mobile operating systems, user interface " +
            "design, mobile app testing, and deployment. Students learn how to develop mobile " +
            "applications using popular mobile platforms such as iOS, Android, and Windows Mobile.")

    if (id == 0)
    {
        val ID = dbManager.insertCourse(values1, values2, values3, values4, values5, values6, values7, values8, values9, values10)
        if (ID>0)
        {

            Toast.makeText(this, "Course is added", Toast.LENGTH_SHORT).show()
            finish()
        }
        else
        {
            Toast.makeText(this, "Error adding course...", Toast.LENGTH_SHORT).show()
        }
    }
    else
    {
        Toast.makeText(this, "ID does not equal 0...", Toast.LENGTH_SHORT).show()
    }
}*/
}