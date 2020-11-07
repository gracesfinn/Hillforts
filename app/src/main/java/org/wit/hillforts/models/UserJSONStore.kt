package org.wit.hillforts.models



import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.user_login.*
import org.jetbrains.anko.AnkoLogger
import org.wit.hillforts.helpers.*
import org.wit.placemark.helpers.exists
import org.wit.placemark.helpers.read
import org.wit.placemark.helpers.write
import java.util.*

val JSON_FILE_USERS = "user.json"
val gsonBuilderUser = GsonBuilder().setPrettyPrinting().create()
val listTypeUser = object : TypeToken<java.util.ArrayList<UserModel>>() {}.type



class UserJSONStore : UserStore, AnkoLogger {

    val context: Context
    var users = mutableListOf<UserModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE_USERS)) {
            deserializeUser()
        }
    }

    override fun findAll(): MutableList<UserModel> {
        return users
    }

    override fun create(user: UserModel) {

        if (exists(context, JSON_FILE)) {
            deserializeUser()
        }
        user.userId = generateRandomId()
        users.add(user)
        serializeUser()
    }

    override fun findUserByEmail(email: String): UserModel? {
        for (each in users) {
            if (email == each.email) return each
        }
        return null
    }


    override fun updateUser(user: UserModel) {

        var currentUser: UserModel? = users.find { u -> u.userId == user.userId }

        for (each in users) {
            if (user.userId == each.userId) currentUser = each
        }

        if (currentUser != null) {
            currentUser.email = user.email
            currentUser.password = user.password
        }

        serializeUser()
    }

    private fun serializeUser() {
        val jsonString = gsonBuilderUser.toJson(users, listTypeUser)
        write(context, JSON_FILE_USERS, jsonString)
    }

    private fun deserializeUser() {
        val jsonString = read(context, JSON_FILE_USERS)
        users = Gson().fromJson(jsonString, listTypeUser)
    }


}


/*override fun seed(user: UserModel) : MutableList<HillfortModel> {
       user.hillforts.add(HillfortModel(
            id = generateRandomId(),
            title = "Coolum",
            description = "The site is located at Beenlea Head, c. 5km SE of Tramore on the SE coast of Co. Waterford.",
            visited = false,
            additionalNotes = "",
            image1 = "src/main/res/drawable/coolum.PNG",
            lat = 52.134562,
            lng = -7.080937,
       ))
       user.hillforts.add(HillfortModel(
           id = generateRandomId(),
           title = "Dunmore",
           description = "The headland, known locally as the ÇBlack Knob can be described as a coastal promontory measuring 130m E-W by 60m N-S, projecting E into Waterford Harbour at an altitude of 8m OD. ",
           visited = false,
           additionalNotes = "",
           image1 = "src/main/res/drawable/dunmore.PNG",
           lat = 52.145954,
           lng = -6.991018,
       ))
       user.hillforts.add(HillfortModel(
           id = generateRandomId(),
           title = "Rathmoylan",
           description = " This coastal promontory is located c. 2.5km SW of Dunmore East town in Co. Waterford. Marked as an ÇEntrenchment on the first edition 6-inch map, the promontory can be described as a triangular area with steep grassy slopes on either flank. ",
           visited = false,
           additionalNotes = "",
           image1 = "src/main/res/drawable/rathmoylan.PNG",
           lat = 52.134654,
           lng = -7.035038,
       ))

       return user.hillforts
    }
} */