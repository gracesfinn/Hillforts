package org.wit.hillforts.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillforts.models.*
import org.wit.hillforts.models.json.UserJSONStore
import org.wit.hillforts.models.room.HillfortStoreRoom

class MainApp : Application(), AnkoLogger {

    lateinit var hillforts: HillfortStore
    lateinit var  users : UserStore

    override fun onCreate() {
        super.onCreate()
        hillforts = HillfortStoreRoom(applicationContext)
        users = UserJSONStore(applicationContext)
        info("Hillfort Tracker started")
    }



}