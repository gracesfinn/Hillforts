package org.wit.hillforts.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import org.wit.hillforts.R
import org.wit.hillforts.main.MainApp

class SplashActivity: AppCompatActivity(){

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.splash_screen)
        app = application as MainApp

        Handler().postDelayed({
            val intent = Intent( this@SplashActivity, HillfortListActivity:: class.java)
            startActivity(intent)
        }, 3000)
    }
}