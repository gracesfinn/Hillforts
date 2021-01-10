package org.wit.hillforts.views.map

import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.activity_hillfort_map.*
import kotlinx.android.synthetic.main.activity_hillfort_map.mapView
import kotlinx.android.synthetic.main.activity_hillfort_map.toolbar
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.new_card.view.*
import org.wit.hillforts.R
import org.wit.hillforts.helpers.readImageFromPath
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.views.BaseView

class HillfortMapView: BaseView(), GoogleMap.OnMarkerClickListener {

    lateinit var presenter: HillfortMapPresenter
    lateinit var map : GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_map)
        super.init(toolbar, true)

        presenter = initPresenter(HillfortMapPresenter(this)) as HillfortMapPresenter

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync {
            map = it
            map.setOnMarkerClickListener(this)
            presenter.loadHillforts()
        }
    }

    override fun showHillfort(hillfort: HillfortModel) {
        currentTitle.text = hillfort.title
        var visitedString =
            if (hillfort.visited == true)
                "Visited"
            else
                "Not Visited"
        currentVisited.text = "${visitedString}"
        Glide.with(this).load(hillfort.image1).into(currentImage);
    }

    override fun showHillforts(hillforts: List<HillfortModel>) {
        presenter.doPopulateMap(map, hillforts)
    }


    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doMarkerSelected(marker)
        return true
    }
    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }


}