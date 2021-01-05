package org.wit.hillforts.views.hillfort

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.activity_hillfort.*
import kotlinx.android.synthetic.main.activity_hillfort.additionalNotes
import kotlinx.android.synthetic.main.activity_hillfort.btnAdd
import kotlinx.android.synthetic.main.activity_hillfort.chooseImage1
import kotlinx.android.synthetic.main.activity_hillfort.chooseImage2
import kotlinx.android.synthetic.main.activity_hillfort.chooseImage3
import kotlinx.android.synthetic.main.activity_hillfort.chooseImage4
import kotlinx.android.synthetic.main.activity_hillfort.dateVisited
import kotlinx.android.synthetic.main.activity_hillfort.description
import kotlinx.android.synthetic.main.activity_hillfort.hillfortImage1
import kotlinx.android.synthetic.main.activity_hillfort.hillfortImage2
import kotlinx.android.synthetic.main.activity_hillfort.hillfortImage3
import kotlinx.android.synthetic.main.activity_hillfort.hillfortImage4
import kotlinx.android.synthetic.main.activity_hillfort.hillfortTitle
import kotlinx.android.synthetic.main.activity_hillfort.toolbarAdd
import kotlinx.android.synthetic.main.activity_hillfort.visited
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.hillforts.R
import org.wit.hillforts.helpers.readImageFromPath
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.Location

import org.wit.hillforts.views.BaseView

class HillfortView: BaseView(),AnkoLogger {

    lateinit var presenter: HillfortPresenter
    var hillfort = HillfortModel()
    lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)

        init(toolbarAdd, true)

        info("Hillfort Activity started..")

        presenter = initPresenter(HillfortPresenter(this)) as HillfortPresenter

        mapView2.onCreate(savedInstanceState);
        mapView2.getMapAsync {
            map = it
            presenter.doConfigureMap(map)
            it.setOnMapClickListener { presenter.doSetLocation()}
        }



        visited.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                hillfort.visited = true

        }

        favourite.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                hillfort.favourite = true

        } //Are these being passed? Need to include rating

        chooseImage1.setOnClickListener {
            presenter.cacheHillfort(
                hillfortTitle.text.toString(),
                description.text.toString(),
                additionalNotes.text.toString(),
                dateVisited.dayOfMonth
            )
            presenter.doSelectImage1()
        }
        chooseImage2.setOnClickListener {
            presenter.cacheHillfort(
                hillfortTitle.text.toString(),
                description.text.toString(),
                additionalNotes.text.toString(),
                dateVisited.dayOfMonth
            )
            presenter.doSelectImage2()
        }
        chooseImage3.setOnClickListener {
            presenter.cacheHillfort(
                hillfortTitle.text.toString(),
                description.text.toString(),
                additionalNotes.text.toString(),
                dateVisited.dayOfMonth
            )
            presenter.doSelectImage3()
        }
        chooseImage4.setOnClickListener {
            presenter.cacheHillfort(
                hillfortTitle.text.toString(),
                description.text.toString(),
                additionalNotes.text.toString(),
                dateVisited.dayOfMonth
            )
            presenter.doSelectImage4()
        }




        btnAdd.setOnClickListener {
            if (hillfortTitle.text.toString().isEmpty()) {
                toast(R.string.enter_hillfort_title)
            } else {
                presenter.doAddOrSave(
                    hillfortTitle.text.toString(),
                    description.text.toString(),
                    additionalNotes.text.toString(),
                    dateVisited.dayOfMonth
                )
            }
        }


    }

    override fun showHillfort(hillfort: HillfortModel) {
       if(hillfortTitle.text.isEmpty()) hillfortTitle.setText(hillfort.title)
        if(description.text.isEmpty()) description.setText(hillfort.description)
        if(additionalNotes.text.isEmpty()) additionalNotes.setText(hillfort.additionalNotes)
        visited.setChecked(hillfort.visited)//Boolean
        favourite.setChecked(hillfort.favourite) //Boolean

        dateVisited.updateDate(hillfort.yearVisited, hillfort.monthVisited, hillfort.dayVisited)

        Glide.with(this).load(hillfort.image1).into(hillfortImage1);
        Glide.with(this).load(hillfort.image2).into(hillfortImage2);
        Glide.with(this).load(hillfort.image3).into(hillfortImage3);
        Glide.with(this).load(hillfort.image4).into(hillfortImage4);

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

        this.showLocation(hillfort.location)

        btnAdd.setText(R.string.save_hillfort)

    }

    override fun showLocation (loc: Location) {
        lat.setText("%.6f".format(loc.lat))
        lng.setText("%.6f".format(loc.lng))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
        if (presenter.edit) menu.getItem(0).setVisible(true)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                presenter.doCancel()
            }
            R.id.item_delete -> {
                presenter.doDelete()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            presenter.doActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onBackPressed() {
        presenter.doCancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView2.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView2.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView2.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView2.onResume()
        presenter.doResartLocationUpdates()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView2.onSaveInstanceState(outState)
    }
}