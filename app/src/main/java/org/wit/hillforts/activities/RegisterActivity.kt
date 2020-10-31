package org.wit.hillforts.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.user_registration.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.wit.hillforts.R
import org.wit.hillforts.sql.DatabaseHelper

class RegisterActivity: AppCompatActivity(), AnkoLogger {

    lateinit var handler: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_registration)
        info("Register Activity started..")


        handler = DatabaseHelper(this)

        userRegistrationBtn.setOnClickListener{
            handler.addUser(name.text.toString(), email_register.text.toString(), password_register.text.toString() )
            startActivityForResult(intentFor<HillfortListActivity>(), 0)

        }

    }
}