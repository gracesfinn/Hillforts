package org.wit.hillforts.models



import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.hillforts.helpers.*
import java.util.*

val JSON_FILE_USERS = "user.json"
val gsonBuilderUser = GsonBuilder().setPrettyPrinting().create()
val listTypeUser = object : TypeToken<java.util.ArrayList<UserModel>>() {}.type

fun generateRandomUserId(): Long {
    return Random().nextLong()
}

class UserJSONStore : UserStore, AnkoLogger {

    val context: Context
    var users = mutableListOf<UserModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<UserModel> {
        return users
    }

    override fun create(user: UserModel) {
        user.userId = generateRandomId()
        users.add(user)
        serialize()
    }


    /*override fun update(placemark: PlacemarkModel) {
        // todo
    } */

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(users, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        users = Gson().fromJson(jsonString, listType)
    }
}