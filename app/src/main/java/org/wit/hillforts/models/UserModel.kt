package org.wit.hillforts.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    var userId: Long = 0,
    var name: String ="",
    var email: String ="",
    var password: String =""
) : Parcelable