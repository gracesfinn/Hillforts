package org.wit.hillforts.models

interface HillfortStore {
    fun findAll(): MutableList<HillfortModel>
    fun create(hillfort:HillfortModel)
    fun update(hillfort: HillfortModel)
    fun delete(hillfort: HillfortModel)
    fun seed()
    fun findById(id:Long) : HillfortModel?
    fun clear()
    fun findFavourites(favourite:Boolean) : List<HillfortModel>

}