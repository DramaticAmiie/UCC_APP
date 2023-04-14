package com.example.uccitapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val mail: String = "ucconline@ucc.edu.jm"
    private val twitterUrl : String = "https://twitter.com/UCCjamaica/"
    private val instagramUrl : String = "https://www.instagram.com/uccjamaica/"
    private val facebookUrl : String = "https://www.facebook.com/uccjamaica/videos/?ref=page_internal"

    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_open_anim
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_close_anim
        )
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.from_bottom_anim
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.to_bottom_anim
        )
    }
    private var clicked = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val socialBtn: FloatingActionButton = findViewById(R.id.btnSocial)
        val emailFAB: FloatingActionButton = findViewById(R.id.fabEmail)
        val facebookBtn: FloatingActionButton = findViewById(R.id.bthFacebook)
        val twitterBtn: FloatingActionButton = findViewById(R.id.btnTwitter)
        val instagramBtn: FloatingActionButton = findViewById(R.id.btnInstagram)
        val imgWelcome: ImageView = findViewById(R.id.imgWelcome)

        fun setVisibility(clicked: Boolean) {
            if (!clicked) {

                emailFAB.visibility = View.VISIBLE
                instagramBtn.visibility = View.VISIBLE
                facebookBtn.visibility = View.VISIBLE
                twitterBtn.visibility = View.VISIBLE
            } else {
                emailFAB.visibility = View.INVISIBLE
                instagramBtn.visibility = View.INVISIBLE
                facebookBtn.visibility = View.INVISIBLE
                twitterBtn.visibility = View.INVISIBLE
            }
        }

        fun setAnimation(clicked: Boolean) {
            if (!clicked) {

                socialBtn.startAnimation(rotateOpen)
                emailFAB.startAnimation(fromBottom)
                instagramBtn.startAnimation(fromBottom)
                facebookBtn.startAnimation(fromBottom)
                twitterBtn.startAnimation(fromBottom)
            } else {
                socialBtn.startAnimation(rotateClose)
                emailFAB.startAnimation(toBottom)
                instagramBtn.startAnimation(toBottom)
                facebookBtn.startAnimation(toBottom)
                twitterBtn.startAnimation(toBottom)
            }
        }

        fun onSocialClicked() {
            setVisibility(clicked)
            setAnimation(clicked)

            clicked = !clicked
        }

        fun onEmailClicked() {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", mail, null))
            intent.putExtra(Intent.EXTRA_EMAIL, mail)
            startActivity(intent)
        }

        fun onFacebookClicked() {
            val intent = Intent(this, FBSocial::class.java)
            intent.putExtra("FACEBOOK", facebookUrl)
            startActivity(intent)
        }

        fun onTwitterClicked() {
            val intent = Intent(this, TSocial::class.java)
            intent.putExtra("TWITTER", twitterUrl)
            startActivity(intent)
        }

        fun onInstagramClicked() {
            val intent = Intent(this, IGSocial::class.java)
            intent.putExtra("INSTAGRAM", instagramUrl)
            startActivity(intent)
        }

        // imgWelcome.setImageResource(R.drawable.group)
        imgWelcome.setBackgroundResource(R.drawable.group)

        socialBtn.setOnClickListener{
            onSocialClicked()
        }

        emailFAB.setOnClickListener {
            onEmailClicked()
        }

        facebookBtn.setOnClickListener {
            onFacebookClicked()
        }

        twitterBtn.setOnClickListener {
            onTwitterClicked()
        }

        instagramBtn.setOnClickListener {
            onInstagramClicked()
        }

        val welcome: TextView = findViewById(R.id.textWelcome)
        welcome.text = "Welcome to the University of the Commonwealth Caribbean"


         val homeBtn : ImageButton = findViewById(R.id.btnHome)
        homeBtn.setOnClickListener{
           /* val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)*/
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
            val intent = Intent(this, CourseActivity::class.java)
            startActivity(intent)
        }


    }

}


