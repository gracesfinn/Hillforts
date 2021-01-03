package org.wit.hillforts.models

interface HillfortStore {
    fun findAll(): List<HillfortModel>
    fun create(hillfort:HillfortModel)
    fun update(hillfort: HillfortModel)
    fun delete(hillfort: HillfortModel)
    fun seed(hillfort: HillfortModel)
    fun findById(id:Long) : HillfortModel?
    //fun findFavourite(favourite:Boolean) : HillfortModel?

}