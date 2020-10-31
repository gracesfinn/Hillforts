package org.wit.hillforts.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.user_login.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillforts.R


class LoginActivity: AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_login)
        info("Login Activity started..")


        userLoginBtn.setOnClickListener(){
            info("Login Button Clicked")
            val intent = Intent(this@LoginActivity, HillfortListActivity::class.java)
            startActivity(intent)
        }

    }
}