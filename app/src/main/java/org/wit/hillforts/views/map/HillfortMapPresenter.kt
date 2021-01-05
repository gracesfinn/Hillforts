package org.wit.hillforts.views.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.views.BasePresenter
import org.wit.hillforts.views.BaseView

class HillfortMapPresenter(view: BaseView) : BasePresenter(view) {


    fun doPopulateMap(map: GoogleMap, hillforts: List<HillfortModel>) {

        map.uiSettings.setZoomControlsEnabled(true)

        hillforts.forEach {
            val loc = LatLng(it.location.lat, it.location.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options).tag = it
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.location.zoom))
        }
    }

    fun doMarkerSelected(marker: Marker) {
        val hillfort = marker.tag as HillfortModel
        doAsync {
            uiThread {
            if (hillfort != null) view?.showHillfort(hillfort)
        }
    }
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