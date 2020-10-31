package org.wit.hillforts.models

data class UserModel(
    val userId: Int =-1,
    val name: String,
    val email: String,
    val password: String
)