package com.emad.movies.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emad.movies.data.local.entities.DetailsEntity
import com.emad.movies.data.local.entities.ReviewEntity

@Dao
interface ReviewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReviews(reviews: List<ReviewEntity>): Array<Long>

    @Query("select * from reviewstable where movieID =:movieID")
    suspend fun getReviews(movieID: Int): List<ReviewEntity>

    @Query("DELETE from reviewstable")
    suspend fun clearDB()
}