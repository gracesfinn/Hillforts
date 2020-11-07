package org.wit.hillforts.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.wit.hillforts.models.HillfortModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.hillforts.R
import org.wit.hillforts.helpers.readImage
import org.wit.hillforts.helpers.readImageFromPath
import org.wit.hillforts.helpers.showImagePicker
import org.wit.hillforts.main.MainApp
import org.wit.hillforts.models.Location
import org.wit.hillforts.models.UserModel
import java.lang.StringBuilder

class HillfortActivity : AppCompatActivity(), AnkoLogger {

    var hillfort = HillfortModel()
    var user = UserModel()
    lateinit var app: MainApp
    val IMAGE_REQUEST1 = 1
    val IMAGE_REQUEST2 = 2
    val IMAGE_REQUEST3 = 3
    val IMAGE_REQUEST4 = 4


    val LOCATION_REQUEST = 5

    //var location = Location(52.245696, -7.139102, 15f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        info("Hillfort Activity started..")




        app = application as MainApp

        var edit = false

        if (intent.hasExtra("hillfort_edit")) {
            edit = true
            hillfort = intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
            hillfortTitle.setText(hillfort.title)
            description.setText(hillfort.description)
            visited.setChecked(hillfort.visited)
            dateVisited.updateDate(hillfort.yearVisited, hillfort.monthVisited, hillfort.dayVisited)

            hillfortImage1.setImageBitmap(readImageFromPath(this, hillfort.image1))
            hillfortImage2.setImageBitmap(readImageFromPath(this, hillfort.image2))
            hillfortImage3.setImageBitmap(readImageFromPath(this, hillfort.image3))
            hillfortImage4.setImageBitmap(readImageFromPath(this, hillfort.image4))

            if (hillfort.image1 != null) {
                chooseImage1.setText(R.string.change_hillfort_image)
            }
            if (hillfort.image2 != null) {
                chooseImage2.setText(R.string.change_hillfort_image)
            }
            if (hillfort.image3 != null) {
                chooseImage3.setText(R.string.change_hillfort_image)
            }
            if (hillfort.image4 != null) {
                chooseImage4.setText(R.string.change_hillfort_image)
            }
            btnAdd.setText(R.string.save_hillfort)
        }

        if (intent.hasExtra("User_edit")) {
            user = intent.extras?.getParcelable<UserModel>("User_edit")!!
        }

        btnAdd.setOnClickListener() {
            hillfort.title = hillfortTitle.text.toString()
            hillfort.description = description.text.toString()
            hillfort.additionalNotes = additionalNotes.text.toString()
            hillfort.dayVisited = dateVisited.dayOfMonth
            hillfort.monthVisited = dateVisited.month
            hillfort.yearVisited = dateVisited.year


            if (hillfort.title.isEmpty()) {
                toast(R.string.enter_hillfort_title)
            } else {
                if (edit) {
                    app.hillforts.update(hillfort.copy())
                } else {
                    app.hillforts.create(hillfort.copy())
                }
            }
            info("add Button Pressed: $hillfortTitle")
            setResult(AppCompatActivity.RESULT_OK)
            startActivityForResult(intentFor<SettingsActivity>().putExtra("User_edit", user), 0)
        }



        visited.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                hillfort.visited = true

        }


        chooseImage1.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST1)
        }

        chooseImage2.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST2)
        }
        chooseImage3.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST3)
        }
        chooseImage4.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST4)
        }

        hillfortLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (hillfort.zoom != 0f) {
                location.lat = hillfort.lat
                location.lng = hillfort.lng
                location.zoom = hillfort.zoom
            }
            startActivityForResult(
                intentFor<MapActivity>().putExtra("location", location),
                LOCATION_REQUEST
            )
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
        return super.onCreateOptionsMenu(menu)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                startActivityForResult(intentFor<HillfortListActivity>().putExtra("User_edit", user), 0)
            }
            R.id.item_delete -> {
                app.hillforts.delete(hillfort)
                startActivityForResult(intentFor<HillfortListActivity>(), 0)
            }
        }
        return super.onOptionsItemSelected(item)
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST1 -> {
                if (data != null) {
                    hillfort.image1 = data.getData().toString()
                    hillfortImage1.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage1.setText(R.string.change_hillfort_image)
                }
            }
            IMAGE_REQUEST2 -> {
                if (data != null) {
                    hillfort.image2 = data.getData().toString()
                    hillfortImage2.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage2.setText(R.string.change_hillfort_image)
                }
            }
            IMAGE_REQUEST3 -> {
                if (data != null) {
                    hillfort.image3 = data.getData().toString()
                    hillfortImage3.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage3.setText(R.string.change_hillfort_image)
                }
            }
            IMAGE_REQUEST4 -> {
                if (data != null) {
                    hillfort.image4 = data.getData().toString()
                    hillfortImage4.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage4.setText(R.string.change_hillfort_image)
                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    val location = data.extras?.getParcelable<Location>("location")!!
                    hillfort.lat = location.lat
                    hillfort.lng = location.lng
                    hillfort.zoom = location.zoom
                }
            }
        }
    }
}