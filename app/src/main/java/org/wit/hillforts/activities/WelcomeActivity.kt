package org.wit.hillforts.activities

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
import org.wit.hillforts.sql.DatabaseHelper


class WelcomeActivity : AppCompatActivity(), AnkoLogger {

    lateinit var handler: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        info("Welcome Activity started..")


       handler= DatabaseHelper(this)



       register.setOnClickListener{
           startActivityForResult(intentFor<RegisterActivity>(), 0)
       }

       // login.setOnClickListener{
         //   showLogin()
        //}


    }



}