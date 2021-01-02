package org.wit.hillforts.views.hillfort

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.hillforts.R
import org.wit.hillforts.activities.HillfortListActivity
import org.wit.hillforts.helpers.readImageFromPath
import org.wit.hillforts.models.HillfortModel

class HillfortView: AppCompatActivity(), AnkoLogger {

    lateinit var presenter: HillfortPresenter
    var hillfort = HillfortModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        info("Hillfort Activity started..")

        presenter = HillfortPresenter(this)


        visited.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                hillfort.visited = true

        }

        favourite.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                hillfort.favourite = true

        } //Are these being passed? Need to include rating

        chooseImage1.setOnClickListener {
            presenter.doSelectImage1()
        }
        chooseImage2.setOnClickListener {
            presenter.doSelectImage2()
        }
        chooseImage3.setOnClickListener {
            presenter.doSelectImage3()
        }
        chooseImage4.setOnClickListener {
            presenter.doSelectImage4()
        }
        hillfortLocation.setOnClickListener {
            presenter.doSetLocation()
        }

        //All Hillfort details being deleted once images are added

        btnAdd.setOnClickListener {
            if (hillfortTitle.text.toString().isEmpty()) {
                toast(R.string.enter_hillfort_title)
            } else {
                presenter.doAddOrSave(hillfortTitle.text.toString(), description.text.toString(), additionalNotes.text.toString(), dateVisited.dayOfMonth)
            }
        }


    }

    fun showHillfort(hillfort: HillfortModel) {
        hillfortTitle.setText(hillfort.title)
        description.setText(hillfort.description)
        additionalNotes.setText(hillfort.additionalNotes)
        visited.setChecked(hillfort.visited)
        favourite.setChecked(hillfort.favourite)

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
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
}