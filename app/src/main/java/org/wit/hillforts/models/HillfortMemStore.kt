package org.wit.hillforts.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import kotlin.math.log

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class HillfortMemStore : HillfortStore, AnkoLogger {

    val hillforts = ArrayList<HillfortModel>()

    override fun findAll(): List<HillfortModel>
    {
        return hillforts
    }

    override fun create(hillfort: HillfortModel){
        hillfort.id = getId()
        hillforts.add(hillfort)
        logAll();
    }

    override fun update(hillfort: HillfortModel) {
        var foundHillfort: HillfortModel?= hillforts.find {h -> h.id == hillfort.id}
        if (foundHillfort != null){
            foundHillfort.title = hillfort.title
            foundHillfort.description = hillfort.description
            foundHillfort.image1 = hillfort.image1
            foundHillfort.lat = hillfort.lat
            foundHillfort.lng = hillfort.lng
            foundHillfort.zoom = hillfort.zoom
            foundHillfort.additionalNotes = hillfort.additionalNotes
            foundHillfort.visited = hillfort.visited
            foundHillfort.dayVisited = hillfort.dayVisited
            foundHillfort.monthVisited = hillfort.monthVisited
            foundHillfort.yearVisited = hillfort.yearVisited
            logAll()
        }
    }

    fun logAll() {
        hillforts.forEach{ info("${it}") }
    }
}