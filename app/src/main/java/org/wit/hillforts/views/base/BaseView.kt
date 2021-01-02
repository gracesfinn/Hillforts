package org.wit.hillforts.views.base

import android.content.Intent
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import org.jetbrains.anko.AnkoLogger
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.UserModel
import org.wit.hillforts.views.hillfort.HillfortView
import org.wit.hillforts.views.hillfortlist.HillfortListView
import org.wit.hillforts.views.location.EditLocationView
import org.wit.hillforts.views.map.HillfortMapView

val IMAGE_REQUEST1 = 1
val IMAGE_REQUEST2 = 2
val IMAGE_REQUEST3 = 3
val IMAGE_REQUEST4 = 4
val LOCATION_REQUEST = 5

enum class VIEW {
    LOCATION, HILLFORT, MAPS, LIST
}
open abstract class BaseView() : AppCompatActivity(), AnkoLogger {
    var basePresenter: BasePresenter? = null

    var user = UserModel()

    fun navigateTo(view: VIEW, code: Int = 0, key: String = "", value: Parcelable? = null) {
        var intent = Intent(this, HillfortListView::class.java)
        when (view) {
            VIEW.LOCATION -> intent = Intent(this, EditLocationView::class.java)
            VIEW.HILLFORT -> intent = Intent(this, HillfortView::class.java).putExtra("User_edit", user)
            VIEW.MAPS -> intent = Intent(this, HillfortMapView::class.java)
            VIEW.LIST -> intent = Intent(this, HillfortListView::class.java)
        }
        if (key != "") {
            intent.putExtra(key, value)
        }
        startActivityForResult(intent, code)
    }

    fun initPresenter(presenter: BasePresenter): BasePresenter {
        basePresenter = presenter
        return presenter
    }

    fun init(toolbar: Toolbar) {
        toolbar.title = title
        setSupportActionBar(toolbar)
    }

    override fun onDestroy() {
        basePresenter?.onDestroy()
        super.onDestroy()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            basePresenter?.doActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        basePresenter?.doRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    open fun showHillfort(hillfort: HillfortModel) {}
    open fun showHillforts(hillforts: List<HillfortModel>) {}
    open fun showProgress() {}
    open fun hideProgress() {}
}



