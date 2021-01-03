package org.wit.hillforts.views.hillfortlist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import kotlinx.android.synthetic.main.app_bar_nav.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.wit.hillforts.R
import org.wit.hillforts.activities.*
import org.wit.hillforts.main.MainApp
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.UserModel
import org.wit.hillforts.views.hillfort.HillfortView
import org.wit.hillforts.views.location.EditLocationView
import org.wit.hillforts.views.map.HillfortMapView

class HillfortListView: AppCompatActivity(), HillfortListener {

    lateinit var presenter: HillfortListPresenter
    var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_list)
        toolbar.title = title
        setSupportActionBar(toolbar)


        presenter = HillfortListPresenter(this)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter =
            HillfortAdapter(presenter.getHillfort(), this)
        recyclerView.adapter?.notifyDataSetChanged()


        addNew.setOnClickListener{
            presenter.doAddHillfort()
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
            R.id.item_navDrawer -> startActivityForResult<NavBarActivity>(0)
            R.id.item_map -> startActivity<HillfortMapView>()
            R.id.item_favourite -> startActivityForResult<FavouriteActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onHillfortClick(hillfort: HillfortModel) {
        presenter.doEditHillfort(hillfort)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }




}