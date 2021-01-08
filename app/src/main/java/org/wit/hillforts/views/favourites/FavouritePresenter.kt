package org.wit.hillforts.views.favourites

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.wit.hillforts.activities.HillfortListener
import org.wit.hillforts.views.BasePresenter
import org.wit.hillforts.views.BaseView

class FavouritePresenter (view: BaseView) : BasePresenter(view){


    fun loadFavHillforts() {
        doAsync {
            val hillforts = app.hillforts.findAll()
            uiThread {
                view?.showFavHillforts(hillforts)
            }
        }
    }
}