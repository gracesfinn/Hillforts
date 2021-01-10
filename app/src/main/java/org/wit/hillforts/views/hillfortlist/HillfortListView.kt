package org.wit.hillforts.views.hillfortlist

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_favourite.*
import kotlinx.android.synthetic.main.activity_hillfort.*
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import kotlinx.android.synthetic.main.activity_hillfort_list.recyclerView

import kotlinx.android.synthetic.main.app_bar_nav.*
import kotlinx.android.synthetic.main.navigation.*
import kotlinx.android.synthetic.main.new_card.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.wit.hillforts.R
import org.wit.hillforts.activities.*
import org.wit.hillforts.hillfortlist.HillfortAdapter
import org.wit.hillforts.hillfortlist.HillfortListener
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.views.BaseView
import org.wit.hillforts.views.VIEW

import org.wit.hillforts.views.location.EditLocationView
import org.wit.hillforts.views.login.LoginView
import org.wit.hillforts.views.map.HillfortMapView

class HillfortListView: BaseView(), HillfortListener {

    lateinit var presenter: HillfortListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_list)
        setSupportActionBar(toolbar)

        bottomAppBar.replaceMenu(R.menu.bottom_app_bar)

        bottomAppBar.setOnMenuItemClickListener{menuItem ->

            when(menuItem.itemId){

                R.id.item_logout -> presenter.doLogout()
                R.id.item_map -> presenter.doShowHillfortsMap()
                R.id.item_favourite -> presenter.doShowFavourites()
                R.id.item_settings -> presenter.doUpdateUser()
                R.id.item_home-> presenter.doShowList()
            }
            true
        }

        presenter = initPresenter(HillfortListPresenter(this)) as HillfortListPresenter

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        presenter.loadHillforts()

        addNew.setOnClickListener{
            presenter.doAddHillfort()
        }

    }


    override fun showHillforts(hillforts: List<HillfortModel>) {
        recyclerView.adapter = HillfortAdapter(hillforts, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }




    override fun onHillfortClick(hillfort: HillfortModel) {
        presenter.doEditHillfort(hillfort)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       presenter.loadHillforts()
        super.onActivityResult(requestCode, resultCode, data)
    }


}