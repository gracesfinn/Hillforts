package org.wit.hillforts.views.hillfortlist

import android.system.Os.remove
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.jetbrains.anko.*
import org.wit.hillforts.helpers.checkLocationPermissions
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.UserModel
import org.wit.hillforts.views.BasePresenter
import org.wit.hillforts.views.BaseView
import org.wit.hillforts.views.VIEW
import org.jetbrains.anko.toast

class HillfortListPresenter(view: BaseView) : BasePresenter(view) {


    var user = UserModel()


    fun doAddHillfort() {
        view?.navigateTo(VIEW.HILLFORT)
    }

    fun doEditHillfort(hillfort: HillfortModel) {
        view?.navigateTo(VIEW.HILLFORT, 0, "hillfort_edit", hillfort)
    }

    fun doShowHillfortsMap() {
        view?.navigateTo(VIEW.MAPS)
    }

    fun doShowFavourites() {
        view?.navigateTo(VIEW.FAVOURITE)
    }

    fun doShowList() {
        view?.navigateTo(VIEW.LIST)
    }

    fun doLogout() {
        FirebaseAuth.getInstance().signOut()
        app.hillforts.clear()
        view?.navigateTo(VIEW.LOGIN)
    }

    fun doUpdateUser() {
        view?.navigateTo(VIEW.SETTINGS)
    }

    fun loadHillforts() {
        doAsync {
            val hillforts = app.hillforts.findAll()
            var favHillforts = mutableListOf<HillfortModel>()
            uiThread {
                if (view!!.intent.hasExtra("favourite")) {
                    view!!.toolbar.title = "Favourite Hillforts"
                    for (hillfort in hillforts) {
                        if (hillfort.favourite == true) {
                            favHillforts.add(hillfort)
                        }
                    }
                    view?.showHillforts(favHillforts)
                }
                else {
                    view?.showHillforts(hillforts)
                }
            }
        }
    }

   /* fun loadHillforts() {
        doAsync {
            val hillforts = app.hillforts.findAll()
            uiThread {
                view?.showHillforts(hillforts)
            }
        }
    } */
    /*fun loadHillforts() {
        val hillforts = app.hillforts.findAll()
        if (view?.intent!!.hasExtra("favourite")) {
            val favHillforts = hillforts.toMutableList()
            for (hillfort: HillfortModel in favHillforts) {
                if (hillfort.favourite == false) {
                    favHillforts.remove(hillfort)
                }
            }

            doAsync {
                val hillforts = app.hillforts.findAll()
                uiThread {
                    if (view!!.intent.hasExtra("favourite")) {
                        view?.showHillforts(favHillforts)
                    } else {
                        view?.showHillforts(hillforts)
                    }

                }
            }

        }

    } */
}