package org.wit.hillforts.models.firebase


import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.jetbrains.anko.AnkoLogger
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.HillfortStore
import org.wit.hillforts.models.Location
import org.wit.hillforts.models.json.generateRandomId

class HillfortFireStore(val context: Context) : HillfortStore, AnkoLogger {

    val hillforts = ArrayList<HillfortModel>()

    lateinit var userId : String
    lateinit var db: DatabaseReference

    override fun findAll(): List<HillfortModel> {
        return hillforts
    }

    override fun findById(id: Long): HillfortModel? {
        val foundHillfort: HillfortModel? = hillforts.find { p -> p.id == id }
        return foundHillfort
    }

    override fun create(hillfort: HillfortModel) {
        val key = db.child("users").child(userId).child("hillforts").push().key
        key?.let {
            hillfort.fbId = key
            hillforts.add(hillfort)
            db.child("users").child(userId).child("hillforts").child(key).setValue(hillfort)
        }
    }

    override fun update(hillfort: HillfortModel) {
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

        }

        db.child("users").child(userId).child("hillforts").child(hillfort.fbId).setValue(hillfort)

    }

    override fun seed(hillfort: HillfortModel) {
        hillforts.add(HillfortModel(
            id = generateRandomId(),
            title = "Coolum",
            description = "The site is located at Beenlea Head, c. 5km SE of Tramore on the SE coast of Co. Waterford.",
            visited = false,
            additionalNotes = "",
            image1 = "src/main/res/drawable/coolum.PNG",
            location = Location(52.134562, -7.080937)

        ))
        hillforts.add(HillfortModel(
            id = generateRandomId(),
            title = "Dunmore",
            description = "The headland, known locally as the ÇBlack Knob can be described as a coastal promontory measuring 130m E-W by 60m N-S, projecting E into Waterford Harbour at an altitude of 8m OD. ",
            visited = false,
            additionalNotes = "",
            image1 = "src/main/res/drawable/dunmore.PNG",
            location = Location(52.145954, -6.991018)

        ))
        hillforts.add(HillfortModel(
            id = generateRandomId(),
            title = "Rathmoylan",
            description = " This coastal promontory is located c. 2.5km SW of Dunmore East town in Co. Waterford. Marked as an ÇEntrenchment on the first edition 6-inch map, the promontory can be described as a triangular area with steep grassy slopes on either flank. ",
            visited = false,
            additionalNotes = "",
            image1 = "src/main/res/drawable/rathmoylan.PNG",
            location = Location(52.134654, -7.035038)
        ))


    }

    override fun delete(hillfort: HillfortModel) {
        db.child("users").child(userId).child("hillforts").child(hillfort.fbId).removeValue()
        hillforts.remove(hillfort)
    }

    override fun clear() {
        hillforts.clear()
    }

    fun fetchHillforts(hillfortsReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot!!.children.mapNotNullTo(hillforts) { it.getValue<HillfortModel>(HillfortModel::class.java) }
                hillfortsReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference
        hillforts.clear()
        db.child("users").child(userId).child("hillforts").addListenerForSingleValueEvent(valueEventListener)
    }
}