package org.wit.hillforts.models.firebase

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.jetbrains.anko.AnkoLogger
import org.wit.hillforts.helpers.readImageFromPath
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.HillfortStore
import org.wit.hillforts.models.Location
import org.wit.hillforts.models.json.generateRandomId
import java.io.ByteArrayOutputStream
import java.io.File

class HillfortFireStore(val context: Context) : HillfortStore, AnkoLogger {

    val hillforts = ArrayList<HillfortModel>()

    lateinit var userId : String
    lateinit var db: DatabaseReference
    lateinit var st: StorageReference



    override fun findAll(): MutableList<HillfortModel> {
        return hillforts
    }

    override fun findById(id: Long): HillfortModel? {
        val foundHillfort: HillfortModel? = hillforts.find { p -> p.id == id }
        return foundHillfort
    }

    override fun findFavourites(favourite: Boolean): List<HillfortModel> {
        // val favouriteHillforts : HillfortModel? = hillforts.find{ p -> p.favourite == true}
        return hillforts
    }

    override fun create(hillfort: HillfortModel) {
        db = FirebaseDatabase.getInstance().reference
        userId = FirebaseAuth.getInstance().currentUser!!.uid
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
            foundHillfort.image2 = hillfort.image2
            foundHillfort.image3 = hillfort.image3
            foundHillfort.image4 = hillfort.image4
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
        if ((hillfort.image1.length) > 0 && (hillfort.image1[0] != 'h')) {
            updateImage(hillfort)
        } else if ((hillfort.image2.length) > 0 && (hillfort.image2[0] != 'h')) {
            updateImage(hillfort)
        }
        else if ((hillfort.image3.length) > 0 && (hillfort.image3[0] != 'h')) {
            updateImage(hillfort)
        }
        else if ((hillfort.image4.length) > 0 && (hillfort.image4[0] != 'h')) {
            updateImage(hillfort)
        }

    }

    override fun seed() {
        val hillfort1 = HillfortModel(
            id = generateRandomId(),
            title = "Coolum",
            description = "The site is located at Beenlea Head, c. 5km SE of Tramore on the SE coast of Co. Waterford.",
            visited = false,
            additionalNotes = "",
            image1 = "https://firebasestorage.googleapis.com/v0/b/hillfort-auth.appspot.com/o/qpklQTe7rmgYsfgdnQYTJTOyww33%2F17?alt=media&token=5ca3f178-5e7d-4fff-8bb6-8de306302e61",
            location = Location(52.134562, -7.080937),
            dayVisited = 1,
            monthVisited = 0,
            yearVisited = 2021

        )
        create(hillfort1)
        val hillfort2 = HillfortModel(
            id = generateRandomId(),
            title = "Dunmore",
            description = "The headland, known locally as the Black Knob can be described as a coastal promontory measuring 130m E-W by 60m N-S, projecting E into Waterford Harbour at an altitude of 8m OD. ",
            visited = false,
            additionalNotes = "",
            image1 = "https://firebasestorage.googleapis.com/v0/b/hillfort-auth.appspot.com/o/h50kDn4SpFZIZRIH6i0xJqh9tkj1%2F19?alt=media&token=7886a9fd-ac4e-4d88-933f-b3755e2b2b30",
            location = Location(52.145954, -6.991018),
            dayVisited = 1,
            monthVisited = 0,
            yearVisited = 2021

        )
        create(hillfort2)
        val hillfort3 = HillfortModel(
            id = generateRandomId(),
            title = "Rathmoylan",
            description = " This coastal promontory is located c. 2.5km SW of Dunmore East town in Co. Waterford. " +
                    "Marked as an Entrenchment on the first edition 6-inch map, the promontory can be described as a triangular area with steep grassy slopes on either flank. ",
            visited = false,
            additionalNotes = "",
            image1 = "https://firebasestorage.googleapis.com/v0/b/hillfort-auth.appspot.com/o/h50kDn4SpFZIZRIH6i0xJqh9tkj1%2F17?alt=media&token=59b58923-b86a-490e-b60f-fd1b4e932983",
            location = Location(52.134654, -7.035038),
            dayVisited = 1,
            monthVisited = 0,
            yearVisited = 2021
        )
        create(hillfort3)

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
        st = FirebaseStorage.getInstance().reference
        hillforts.clear()
        db.child("users").child(userId).child("hillforts").addListenerForSingleValueEvent(valueEventListener)
    }

    fun updateImage(hillfort: HillfortModel) {
        if (hillfort.image1 != "") {
            val fileName = File(hillfort.image1)
            val imageName = fileName.getName()

            var imageRef1 = st.child(userId + '/' + imageName)
            val baos = ByteArrayOutputStream()
            val bitmap = readImageFromPath(context, hillfort.image1)

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef1.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        hillfort.image1 = it.toString()
                        db.child("users").child(userId).child("hillforts").child(hillfort.fbId).setValue(hillfort)
                    }
                }
            }
        }
        if (hillfort.image2 != "") {
            val fileName = File(hillfort.image2)
            val imageName = fileName.getName()

            var imageRef2 = st.child(userId + '/' + imageName)
            val baos = ByteArrayOutputStream()
            val bitmap = readImageFromPath(context, hillfort.image2)

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef2.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        hillfort.image2 = it.toString()
                        db.child("users").child(userId).child("hillforts").child(hillfort.fbId).setValue(hillfort)
                    }
                }
            }
        }
        if (hillfort.image3 != "") {
            val fileName = File(hillfort.image3)
            val imageName = fileName.getName()

            var imageRef3 = st.child(userId + '/' + imageName)
            val baos = ByteArrayOutputStream()
            val bitmap = readImageFromPath(context, hillfort.image3)

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef3.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        hillfort.image3 = it.toString()
                        db.child("users").child(userId).child("hillforts").child(hillfort.fbId).setValue(hillfort)
                    }
                }
            }
        }
        if (hillfort.image4 != "") {
            val fileName = File(hillfort.image4)
            val imageName = fileName.getName()

            var imageRef4 = st.child(userId + '/' + imageName)
            val baos = ByteArrayOutputStream()
            val bitmap = readImageFromPath(context, hillfort.image4)

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef4.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        hillfort.image4 = it.toString()
                        db.child("users").child(userId).child("hillforts").child(hillfort.fbId).setValue(hillfort)
                    }
                }
            }
        }
    }
}