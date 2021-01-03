package org.wit.hillforts.views.hillfortlist

import org.jetbrains.anko.*
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.UserModel
import org.wit.hillforts.views.base.BasePresenter
import org.wit.hillforts.views.base.BaseView
import org.wit.hillforts.views.base.VIEW

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
        doAsync {
            val hillforts = app.hillforts.findAll()
            uiThread {
                view?.showHillforts(hillforts)
            }
        }

    }

}