package org.wit.hillforts.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.user_registration.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

import org.wit.hillforts.R
import org.wit.hillforts.main.MainApp
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.UserModel


class RegisterActivity: AppCompatActivity(), AnkoLogger {

    var user = UserModel()
    lateinit var app: MainApp
    var hillfort = HillfortModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_registration)

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        info("Register Activity started..")
        app = application as MainApp


        userRegistrationBtn.setOnClickListener(){

            user.name = name.text.toString()
            user.email = email_register.text.toString()
            user.password = password_register.text.toString()
            info("Reg Button Clicked")

            if(user.name.isNotEmpty() && user.email.isNotEmpty() && user.password.isNotEmpty()) {
                app.users.create(user.copy())
                info("Username:  ${user} was registered")
                startActivityForResult(intentFor<WelcomeActivity>().putExtra("User", user), 0)
                app.hillforts.seed(hillfort)
            }
           else{
                toast(R.string.registration_error)
            }


        }

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cancel, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                val intent = Intent(this@RegisterActivity, WelcomeActivity::class.java)
                startActivity(intent)
                toast("Registration Cancelled")
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}