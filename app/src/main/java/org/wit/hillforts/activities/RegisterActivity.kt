package org.wit.hillforts.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.user_registration.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

import org.wit.hillforts.R


class RegisterActivity: AppCompatActivity(), AnkoLogger {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_registration)
        info("Register Activity started..")


        userRegistrationBtn.setOnClickListener(){
            info("Reg Button Clicked")
            val intent = Intent(this@RegisterActivity, WelcomeActivity::class.java)
            startActivity(intent)
        }

    }
}