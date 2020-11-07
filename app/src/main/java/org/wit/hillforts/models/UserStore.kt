package org.wit.hillforts.models

interface UserStore {
    fun findAll(): List<UserModel>
    fun create(user:UserModel)
    fun findUserByEmail(email: String): UserModel?
    fun updateUser(user: UserModel)
}