package org.wit.hillforts.models

interface UserStore {
    fun findAll(): List<UserModel>
    fun create(user:UserModel)
   // fun update(hillfort: UserModel)
}