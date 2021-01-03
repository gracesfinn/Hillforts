package org.wit.hillforts.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HillfortModel(
    var userId : Long = 0,
    var id: Long = 0,
    var title: String = "",
    var description: String = "",
    var visited: Boolean = false,
    var favourite : Boolean = false,
    var dayVisited: Int= 0,
    var monthVisited: Int= 0,
    var yearVisited: Int= 0,
    var additionalNotes: String = "",
    var rating: Float = 0f,
    var image1: String = "",
    var image2: String = "",
    var image3: String = "",
    var image4: String = "",
    var lat : Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f
): Parcelable

@Parcelize
data class Location(
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f
) : Parcelable

@Parcelize
data class UserModel(
    var userId: Long = 0,
    var name: String ="",
    var email: String ="",
    var password: String ="",
    //var hillforts: MutableList<HillfortModel>
) : Parcelable