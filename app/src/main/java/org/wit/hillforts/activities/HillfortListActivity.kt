package org.wit.hillforts.activities


import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_hillfort_list.*
import kotlinx.android.synthetic.main.user_login.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor

import org.jetbrains.anko.startActivityForResult
import org.wit.hillforts.R
import org.wit.hillforts.main.MainApp
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.UserModel


class HillfortListActivity : AppCompatActivity(), AnkoLogger, HillfortListener {

    lateinit var app: MainApp
    var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_list)
        app = application as  MainApp

        toolbar.title = title
        setSupportActionBar(toolbar)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        loadHillforts()

        addNew.setOnClickListener()
        {
            info("Add New Button Clicked")
            startActivityForResult(intentFor<HillfortActivity>().putExtra("User_edit", user), 0)
        }

        if (intent.hasExtra("User_edit")) {
            user = intent.extras?.getParcelable<UserModel>("User_edit")!!
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {

            R.id.item_settings -> startActivityForResult(intentFor<SettingsActivity>().putExtra("User_edit", user), 0)
            R.id.item_logout -> startActivityForResult<WelcomeActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onHillfortClick(hillfort: HillfortModel) {
        startActivityForResult(intentFor<HillfortActivity>().putExtra("hillfort_edit", hillfort).putExtra("User_edit", user), 0)
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadHillforts()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadHillforts() {
        showHillforts(app.hillforts.findAll())
    }

    fun showHillforts (hillforts: List<HillfortModel>) {
        recyclerView.adapter = HillfortAdapter(hillforts, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

}




