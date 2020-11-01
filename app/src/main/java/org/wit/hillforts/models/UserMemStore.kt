package org.wit.hillforts.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastUserId = 0L

internal fun getUserId(): Long {
    return lastUserId++
}

class UserMemStore : UserStore, AnkoLogger {

    val users = ArrayList<UserModel>()

    override fun findAll(): List<UserModel>
    {
        return users
    }

    override fun create(user: UserModel) {
        user.userId = getUserId()
        users.add(user)
        logAll()
    }


    fun logAll() {
        users.forEach{ info("${it}") }
    }
}