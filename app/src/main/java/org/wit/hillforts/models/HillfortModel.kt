package org.wit.hillforts.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HillfortModel(var id: Long = 0,
                         var title: String = "",
                         var description: String = "",
                         var location: Float = 0.0f,
                        var visited: Boolean = false,
                        var dateVisited: Int= 0,
                        var additionalNotes: String = "",
                         var image: String = ""): Parcelable