package org.wit.hillforts.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_hillfort.*

import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_settings.toolbarAdd

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.hillforts.R
import org.wit.hillforts.main.MainApp
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.UserModel

class SettingsActivity: AppCompatActivity(), AnkoLogger {
    lateinit var app: MainApp
    var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lateinit var app: MainApp



        setContentView(R.layout.activity_settings)
        app = application as MainApp
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        info("Update Activity started..")



        if (intent.hasExtra("User_edit"))
        {
            val currentUser = intent.extras?.getParcelable<UserModel>("User_edit")!!
            user = app.users.findUserByEmail(currentUser.email)!!
        }



        settingsBtn.setOnClickListener() {
            user.email = update_email.text.toString()
            user.password = update_password.text.toString()

            if (user.email.isEmpty() && user.password.isEmpty())
                toast(getString(R.string.registration_error))
            else {
                    app.users.updateUser(user.copy())
                info("Username:  ${user} was updated")
            }

            info("Update Button Clicked")
            startActivityForResult(intentFor<HillfortListActivity>().putExtra("User", user), 0)
            toast("Details Updated")
        }
    }





    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_up, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_up -> {
                startActivityForResult( intentFor<HillfortListActivity>().putExtra("User_edit", user), 0)
                toast("Update Cancelled")

            }
        }
        return super.onOptionsItemSelected(item)
    }
}