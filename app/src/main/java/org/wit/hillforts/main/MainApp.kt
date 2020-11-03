package org.wit.hillforts.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillforts.models.*

class MainApp : Application(), AnkoLogger {

    lateinit var hillforts: HillfortStore
    lateinit var  users : UserStore

    override fun onCreate() {
        super.onCreate()
        hillforts = HillfortJSONStore(applicationContext)
        users = UserMemStore()
        info("Hillfort Tracker started")
    }



}