package org.wit.hillforts.models.json

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.hillforts.helpers.*
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.HillfortStore
import org.wit.hillforts.models.Location
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

   override fun seed() {

       serialize()
    }

    override fun delete(hillfort: HillfortModel) {
        val foundHillfort: HillfortModel? = hillforts.find {it.id == hillfort.id }
        hillforts.remove(foundHillfort)
        serialize()
    }



    override fun update(hillfort: HillfortModel) {
        val hillfortList = findAll() as ArrayList<HillfortModel>
        var foundHillfort: HillfortModel?= hillforts.find {h -> h.id == hillfort.id}
        if (foundHillfort != null){
            foundHillfort.title = hillfort.title
            foundHillfort.description = hillfort.description
            foundHillfort.image1 = hillfort.image1
            foundHillfort.location = hillfort.location
            foundHillfort.additionalNotes = hillfort.additionalNotes
            foundHillfort.visited = hillfort.visited
            foundHillfort.favourite = hillfort.favourite
            foundHillfort.rating = hillfort.rating
            foundHillfort.dayVisited = hillfort.dayVisited
            foundHillfort.monthVisited = hillfort.monthVisited
            foundHillfort.yearVisited = hillfort.yearVisited
            serialize()

        }

    }

    override fun findFavourites(favourite: Boolean):  MutableList<HillfortModel>
    {
       // val favouriteHillfort: HillfortModel? = hillforts.find {it.favourite == true}
        return  hillforts
    }

    override fun findById(id: Long): HillfortModel? {
        val foundHillfort: HillfortModel? = hillforts.find {it.id == id}
        return foundHillfort
    }



    private fun serialize() {
        val jsonString = gsonBuilder.toJson( hillforts, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        hillforts = Gson().fromJson(jsonString, listType)
    }
    override fun clear(){
        hillforts.clear()
    }




}