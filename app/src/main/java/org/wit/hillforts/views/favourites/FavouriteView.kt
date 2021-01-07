package org.wit.hillforts.views.favourites

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.wit.hillforts.R
import org.wit.hillforts.activities.HillfortAdapter
import org.wit.hillforts.activities.HillfortListener
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.views.BaseView
import org.wit.hillforts.views.hillfortlist.HillfortListPresenter

abstract class FavouriteView : BaseView(), HillfortListener {

    lateinit var presenter: FavouritePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)
        setSupportActionBar(toolbar)
        super.init(toolbar, false)

        presenter = initPresenter(FavouritePresenter(this)) as FavouritePresenter

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        presenter.loadFavHillforts()
    }

    override fun showFavHillforts(hillforts: List<HillfortModel>) {
        recyclerView.adapter = HillfortAdapter(hillforts, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}