package org.wit.hillforts.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_settings.toolbarAdd

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.hillforts.R
import org.wit.hillforts.main.MainApp

class SettingsActivity: AppCompatActivity(), AnkoLogger {
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)


        app = application as MainApp
        info("Update Activity started..")


        settingsBtn.setOnClickListener(){
            info("Login Button Clicked")
            val intent = Intent(this@SettingsActivity, HillfortListActivity::class.java)
            startActivity(intent)
            toast("Details Updated")
        }



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cancel, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                val intent = Intent(this@SettingsActivity, HillfortListActivity::class.java)
                startActivity(intent)
                toast("Update Cancelled")
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}