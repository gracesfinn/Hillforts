package org.wit.hillforts.models.room

import androidx.room.*
import org.wit.hillforts.models.HillfortModel


@Dao
interface HillfortDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(hillfort:HillfortModel)


    @Query("SELECT * FROM HillfortModel")
    fun findAll(): List<HillfortModel>

    @Query("select * from HillfortModel where id = :id")
    fun findById(id: Long): HillfortModel

    @Query("select * from HillfortModel where favourite = :favourite")
    fun findFavourites(favourite: Boolean): List<HillfortModel>

    @Update
    fun update(hillfort: HillfortModel)

    @Delete
    fun deleteHillfort(hillfort: HillfortModel)

    @Insert
    fun seedHillfort(hillfort: HillfortModel)

}