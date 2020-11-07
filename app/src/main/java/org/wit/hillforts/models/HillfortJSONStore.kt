package org.wit.hillforts.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.hillforts.helpers.*
import org.wit.placemark.helpers.exists
import org.wit.placemark.helpers.read
import org.wit.placemark.helpers.write
import java.util.*

val JSON_FILE = "hillforts.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<HillfortModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class HillfortJSONStore : HillfortStore, AnkoLogger {
    val context: Context
    var hillforts = mutableListOf<HillfortModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }


    override fun findAll(): MutableList<HillfortModel> {
        return  hillforts
    }

    override fun create(hillfort: HillfortModel) {
        hillfort.id = generateRandomId()
        hillforts.add( hillfort)
        serialize()
    }

   override fun seed(hillfort: HillfortModel) {
       hillforts.add(HillfortModel(
            id = generateRandomId(),
            title = "Coolum",
            description = "The site is located at Beenlea Head, c. 5km SE of Tramore on the SE coast of Co. Waterford.",
            visited = false,
            additionalNotes = "",
            image1 = "src/main/res/drawable/coolum.PNG",
            lat = 52.134562,
            lng = -7.080937,
       ))
       hillforts.add(HillfortModel(
           id = generateRandomId(),
           title = "Dunmore",
           description = "The headland, known locally as the ÇBlack Knob can be described as a coastal promontory measuring 130m E-W by 60m N-S, projecting E into Waterford Harbour at an altitude of 8m OD. ",
           visited = false,
           additionalNotes = "",
           image1 = "src/main/res/drawable/dunmore.PNG",
           lat = 52.145954,
           lng = -6.991018,
       ))
       hillforts.add(HillfortModel(
           id = generateRandomId(),
           title = "Rathmoylan",
           description = " This coastal promontory is located c. 2.5km SW of Dunmore East town in Co. Waterford. Marked as an ÇEntrenchment on the first edition 6-inch map, the promontory can be described as a triangular area with steep grassy slopes on either flank. ",
           visited = false,
           additionalNotes = "",
           image1 = "src/main/res/drawable/rathmoylan.PNG",
           lat = 52.134654,
           lng = -7.035038,
       ))

       serialize()
    }

    override fun delete(hillfort: HillfortModel) {
        hillforts.remove(hillfort)
        serialize()
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
            serialize()

        }

    }



    private fun serialize() {
        val jsonString = gsonBuilder.toJson( hillforts, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        hillforts = Gson().fromJson(jsonString, listType)
    }




}