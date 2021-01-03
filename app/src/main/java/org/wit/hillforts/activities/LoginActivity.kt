package org.wit.hillforts.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.user_login.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.hillforts.R
import org.wit.hillforts.main.MainApp
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.UserModel
import org.wit.hillforts.views.hillfortlist.HillfortListView


class LoginActivity: AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    var user = UserModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_login)
        info("Login Activity started..")

        app = application as MainApp




        userLoginBtn.setOnClickListener(){
            var currentUser = app.users.findUserByEmail(login_email.text.toString())
            if (currentUser == null) toast(getString(R.string.login_email_error))
            else {
                user = currentUser
                if (login_password.text.toString() == user.password){

                    startActivityForResult( intentFor<HillfortListView>().putExtra("User_edit", user), 0)
                    }
                else  toast(getString(R.string.login_email_error))
            }
            info("Login Button Clicked")

        }

        userLoginRegBtn.setOnClickListener(){
            info("Login Reg Button Clicked")
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}