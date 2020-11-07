package org.wit.hillforts.models

interface UserStore {
    fun findAll(): List<UserModel>
    fun create(user:UserModel)
    fun findUserByEmail(email: String): UserModel?
    fun updateUser(user: UserModel)
   // fun seed (user: UserModel) : MutableList<HillfortModel>

}