package org.wit.hillforts.views.hillfortlist

import androidx.core.app.ActivityCompat.startActivityForResult
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.wit.hillforts.activities.HillfortActivity
import org.wit.hillforts.main.MainApp
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.UserModel
import org.wit.hillforts.views.hillfort.HillfortView
import org.wit.hillforts.views.location.EditLocationView
import org.wit.hillforts.views.map.HillfortMapView

class HillfortListPresenter(val view: HillfortListView) {

    var app: MainApp
    var user = UserModel()

    init{
        app= view.application as MainApp
    }

    fun getHillfort() = app.hillforts.findAll()

    fun doAddHillfort()
    {
        view.startActivityForResult(view.intentFor<HillfortView>().putExtra("User_edit", user), 0)
    }

    fun doEditHillfort(hillfort: HillfortModel){
        view.startActivityForResult(view.intentFor<HillfortView>().putExtra("hillfort_edit", hillfort).putExtra("User_edit", user), 0)
    }

    fun doShowHillfortsMap(){
        view.startActivity<HillfortMapView>()
    }

}