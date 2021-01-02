package org.wit.hillforts.views.hillfortlist

import androidx.core.app.ActivityCompat.startActivityForResult
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.wit.hillforts.activities.HillfortActivity
import org.wit.hillforts.main.MainApp
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.UserModel
import org.wit.hillforts.views.base.BasePresenter
import org.wit.hillforts.views.base.BaseView
import org.wit.hillforts.views.base.VIEW
import org.wit.hillforts.views.hillfort.HillfortView
import org.wit.hillforts.views.location.EditLocationView
import org.wit.hillforts.views.map.HillfortMapView

class HillfortListPresenter(view: BaseView) : BasePresenter(view) {


    var user = UserModel()


    fun doAddHillfort()
    {
        view?.navigateTo(VIEW.HILLFORT)
    }

    fun doEditHillfort(hillfort: HillfortModel){
        view?.navigateTo(VIEW.HILLFORT, 0, "hillfort_edit", hillfort)
    }

    fun doShowHillfortsMap(){
        view?.navigateTo(VIEW.MAPS)
    }

    fun loadHillforts() {
        view?.showHillforts(app.hillforts.findAll())
    }

}