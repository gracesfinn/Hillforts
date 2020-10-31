package org.wit.hillforts.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlinx.android.synthetic.main.user_login.*
import kotlinx.android.synthetic.main.user_registration.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.wit.hillforts.R



class WelcomeActivity : AppCompatActivity(), AnkoLogger {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        info("Welcome Activity started..")


       register.setOnClickListener{
           val intent = Intent(this@WelcomeActivity, RegisterActivity::class.java)
           startActivity(intent)
       }

       login.setOnClickListener{
           val intent = Intent(this@WelcomeActivity, LoginActivity::class.java)
           startActivity(intent)
        }


    }



}