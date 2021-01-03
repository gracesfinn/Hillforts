package org.wit.hillforts.views.hillfort

import android.annotation.SuppressLint
import android.content.Intent
import android.provider.ContactsContract
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.hillforts.R
import org.wit.hillforts.activities.HillfortListActivity
import org.wit.hillforts.activities.MapActivity
import org.jetbrains.anko.info
import org.wit.hillforts.activities.HillfortActivity
import org.wit.hillforts.helpers.*
import org.wit.hillforts.main.MainApp
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.Location
import org.wit.hillforts.models.UserModel
import org.wit.hillforts.views.base.*
import org.wit.hillforts.views.hillfortlist.HillfortListView
import org.wit.hillforts.views.location.EditLocationView

class HillfortPresenter (view: BaseView) : BasePresenter(view) {

    var hillfort = HillfortModel()
    var user = UserModel()
    var defaultLocation = Location(52.245696, -7.139102, 15f)
    var edit = false
    var map: GoogleMap? = null
    var locationService: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view)
    val locationRequest = createDefaultLocationRequest()


    init {
        if (view.intent.hasExtra("hillfort_edit")) {
            edit = true
            hillfort = view.intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
            view.showHillfort(hillfort)
        } else {
            if (checkLocationPermissions(view)) {
                doSetCurrentLocation()
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun doSetCurrentLocation() {
        locationService.lastLocation.addOnSuccessListener {
            locationUpdate(it.latitude, it.longitude)
        }
    }

    override fun doRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
            if (isPermissionGranted(requestCode, grantResults)) {
               doSetCurrentLocation()
            } else {
                // permissions denied, so use the default location
                locationUpdate(defaultLocation.lat, defaultLocation.lng)
            }
        }

    @SuppressLint("MissingPermission")
    fun doResartLocationUpdates() {
        var locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null && locationResult.locations != null) {
                    val l = locationResult.locations.last()
                    locationUpdate(l.latitude, l.longitude)
                }
            }
        }
        if (!edit) {
            locationService.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    fun doConfigureMap(m: GoogleMap) {
        map = m
        locationUpdate(hillfort.lat, hillfort.lng)
    }

    fun locationUpdate(lat: Double, lng: Double) {
        hillfort.lat = lat
        hillfort.lng = lng
        hillfort.zoom = 15f
        map?.clear()
        map?.uiSettings?.setZoomControlsEnabled(true)
        val options = MarkerOptions().title(hillfort.title).position(LatLng(hillfort.lat, hillfort.lng))
        map?.addMarker(options)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(hillfort.lat, hillfort.lng), hillfort.zoom))
        view?.showHillfort(hillfort)
    }

    fun doAddOrSave(
        hillfortTitle: String,
        description: String,
        additionalNotes: String,
        dateVisited: Int,

    ) {
        hillfort.title = hillfortTitle
        hillfort.description = description
        hillfort.additionalNotes = additionalNotes
        hillfort.dayVisited = dateVisited
        hillfort.monthVisited = dateVisited
        hillfort.yearVisited = dateVisited

        if (edit) {
            app.hillforts.update(hillfort)
        } else {
            app.hillforts.create(hillfort)
        }

        view?.startActivityForResult(
            view?.intentFor<HillfortListView>()?.putExtra("User_edit", user), 0)
        view?.finish()

    }


    fun doCancel() {
        view?.finish()
    }

    fun doDelete() {
        app.hillforts.delete(hillfort)
        view?.finish()
    }

    fun cacheHillfort(
        title: String,
        description: String,
        additionalNotes: String,
        dateVisited: Int)
    {
        hillfort.title = title
        hillfort.description = description
        hillfort.additionalNotes = additionalNotes
        hillfort.dayVisited = dateVisited
        hillfort.monthVisited = dateVisited
        hillfort.yearVisited = dateVisited
    }

    fun doSelectImage1() {
        showImagePicker(view!!, IMAGE_REQUEST1)
    }

    fun doSelectImage2() {
        showImagePicker(view!!, IMAGE_REQUEST2)
    }

    fun doSelectImage3() {
        showImagePicker(view!!, IMAGE_REQUEST3)
    }

    fun doSelectImage4() {
        showImagePicker(view!!, IMAGE_REQUEST4)
    }

    fun doCheckVisited(){
//  Need to finish
    }

    fun doSetLocation() {
        view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", Location(hillfort.lat, hillfort.lng, hillfort.zoom))
    }



    override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST1 -> {
                if (data != null) {
                    hillfort.image1 = data.getData().toString()
                    view?.showHillfort(hillfort)
                }
            }
            IMAGE_REQUEST2 -> {
                if (data != null) {
                    hillfort.image2 = data.getData().toString()
                    view?.showHillfort(hillfort)
                }
            }
            IMAGE_REQUEST3 -> {
                if (data != null) {
                    hillfort.image3 = data.getData().toString()
                    view?.showHillfort(hillfort)
                }
            }
            IMAGE_REQUEST4 -> {
                if (data != null) {
                    hillfort.image4 = data.getData().toString()
                    view?.showHillfort(hillfort)
                }
            }
            LOCATION_REQUEST -> {
                val location = data.extras?.getParcelable<Location>("location")!!
                hillfort.lat = location.lat
                hillfort.lng = location.lng
                hillfort.zoom = location.zoom
                locationUpdate(hillfort.lat, hillfort.lng)
            }
        }
    }
}


