package org.wit.hillforts.views.hillfort

import android.annotation.SuppressLint
import android.content.Intent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.*
import org.wit.hillforts.helpers.*
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.Location
import org.wit.hillforts.models.UserModel
import org.wit.hillforts.views.*
import org.wit.hillforts.views.hillfortlist.HillfortListView
import java.time.Month

class HillfortPresenter (view: BaseView) : BasePresenter(view) {

    var hillfort = HillfortModel()
    var user = UserModel()
    var defaultLocation = Location(52.245696, -7.139102, 15f)
    var edit = false
    var map: GoogleMap? = null
    var locationService: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view)
    val locationRequest = createDefaultLocationRequest()
    var locationManualyChanged = false;


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
            locationUpdate(Location(it.latitude, it.longitude))
        }
    }

    override fun doRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
            if (isPermissionGranted(requestCode, grantResults)) {
               doSetCurrentLocation()
            } else {
                locationUpdate(defaultLocation)
            }
        }

    @SuppressLint("MissingPermission")
    fun doResartLocationUpdates() {
        var locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null && locationResult.locations != null) {
                    val l = locationResult.locations.last()
                    if (!locationManualyChanged) {
                        locationUpdate(Location(l.latitude, l.longitude))
                    }
                }
            }
        }
        if (!edit) {
            locationService.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    fun doConfigureMap(m: GoogleMap) {
        map = m
        locationUpdate(hillfort.location)
    }

    fun locationUpdate(location: Location) {
        hillfort.location = location
        hillfort.location.zoom = 15f
        map?.clear()
        map?.uiSettings?.setZoomControlsEnabled(true)
        val options = MarkerOptions().title(hillfort.title).position(LatLng(hillfort.location.lat, hillfort.location.lng))
        map?.addMarker(options)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(hillfort.location.lat, hillfort.location.lng), hillfort.location.zoom))
        view?.showLocation(hillfort.location)
    }

    fun doAddOrSave(
        hillfortTitle: String,
        description: String,
        additionalNotes: String,
        dayOfMonth: Int,
        month: Int,
        year: Int
    ) {
        hillfort.title = hillfortTitle
        hillfort.description = description
        hillfort.additionalNotes = additionalNotes
        hillfort.dayVisited = dayOfMonth
        hillfort.monthVisited = month
        hillfort.yearVisited = year


       doAsync {
           if (edit) {
               app.hillforts.update(hillfort)
           } else {
               app.hillforts.create(hillfort)
           }
           uiThread { view?.finish() }
       }
        view?.startActivityForResult(
            view?.intentFor<HillfortListView>()?.putExtra("User_edit", user), 0
        )


    }


    fun doCancel() {
        view?.finish()
    }

    fun doDelete() {
        doAsync {
            app.hillforts.delete(hillfort)
            uiThread {
                view?.finish()
            }
        }
    }

    fun cacheHillfort(
        hillfortTitle: String,
        description: String,
        additionalNotes: String,
        dayOfMonth: Int,
        month: Int,
        year: Int,
        )
    {
        hillfort.title = hillfortTitle
        hillfort.description = description
        hillfort.additionalNotes = additionalNotes
        hillfort.dayVisited = dayOfMonth
        hillfort.monthVisited = month
        hillfort.yearVisited = year

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

    fun doCheckVisited(
        visited : Boolean
    ){
            hillfort.visited = visited

    }


    fun doCheckFavourite(
        favourite : Boolean
    ){
        hillfort.favourite = favourite
    }

   fun doCheckRatingBar(
    rating:Float
    ){
        hillfort.rating = rating
    }

    fun doSetLocation() {
        locationManualyChanged = true;
        view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", Location(hillfort.location.lat, hillfort.location.lng, hillfort.location.zoom))
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
                hillfort.location = location
                locationUpdate(location)
            }
        }
    }
}


