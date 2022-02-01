package com.emad.movies.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emad.movies.data.local.entities.DetailsEntity
import com.emad.movies.data.local.entities.MovieEntity

@Dao
interface DetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDetails(details: DetailsEntity): Long

    @Query("select * from detailstable where movieID =:movieID")
    suspend fun getDetails(movieID: Int): DetailsEntity

    @Query("DELETE from detailstable")
    suspend fun clearDB()
}