package org.wit.hillforts.views.hillfort

import android.content.Intent
import android.provider.ContactsContract
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.hillforts.R
import org.wit.hillforts.activities.HillfortListActivity
import org.wit.hillforts.activities.MapActivity
import org.wit.hillforts.helpers.readImage
import org.wit.hillforts.helpers.readImageFromPath
import org.jetbrains.anko.info
import org.wit.hillforts.activities.HillfortActivity
import org.wit.hillforts.helpers.showImagePicker
import org.wit.hillforts.main.MainApp
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.Location
import org.wit.hillforts.models.UserModel
import org.wit.hillforts.views.hillfortlist.HillfortListView
import org.wit.hillforts.views.location.EditLocationView

class HillfortPresenter (val view: HillfortView) {

    var hillfort = HillfortModel()
    var user = UserModel()
    lateinit var app: MainApp
    val IMAGE_REQUEST1 = 1
    val IMAGE_REQUEST2 = 2
    val IMAGE_REQUEST3 = 3
    val IMAGE_REQUEST4 = 4
    val LOCATION_REQUEST = 5
    var edit = false
    var location = Location(52.245696, -7.139102, 15f)


    init {
        app = view.application as MainApp



        if (view.intent.hasExtra("hillfort_edit")) {
            edit = true
            hillfort = view.intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
        }
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

        view.startActivityForResult(
            view.intentFor<HillfortListView>().putExtra("User_edit", user), 0)
        view.finish()

    }


    fun doCancel() {
        view.finish()
    }

    fun doDelete() {
        app.hillforts.delete(hillfort)
        view.finish()
    }

    fun doSelectImage1() {
        showImagePicker(view, IMAGE_REQUEST1)
    }

    fun doSelectImage2() {
        showImagePicker(view, IMAGE_REQUEST2)
    }

    fun doSelectImage3() {
        showImagePicker(view, IMAGE_REQUEST3)
    }

    fun doSelectImage4() {
        showImagePicker(view, IMAGE_REQUEST4)
    }

    fun doCheckVisited(){

    }

    fun doSetLocation() {
        if (hillfort.zoom != 0f) {
            hillfort.lat = hillfort.lat
            hillfort.lng = hillfort.lng
            hillfort.zoom = hillfort.zoom
        }
        view.startActivityForResult(
            view.intentFor<EditLocationView>().putExtra("location", location), LOCATION_REQUEST
        )
    }

    fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST1 -> {
                if (data != null) {
                    hillfort.image1 = data.getData().toString()
                    view.showHillfort(hillfort)
                }
            }
            IMAGE_REQUEST2 -> {
                if (data != null) {
                    hillfort.image2 = data.getData().toString()
                    view.showHillfort(hillfort)
                }
            }
            IMAGE_REQUEST3 -> {
                if (data != null) {
                    hillfort.image3 = data.getData().toString()
                    view.showHillfort(hillfort)
                }
            }
            IMAGE_REQUEST4 -> {
                if (data != null) {
                    hillfort.image4 = data.getData().toString()
                    view.showHillfort(hillfort)
                }
            }
            LOCATION_REQUEST -> {
                location = data.extras?.getParcelable<Location>("location")!!
                hillfort.lat = location.lat
                hillfort.lng = location.lng
                hillfort.zoom = location.zoom
            }
        }
    }
}


