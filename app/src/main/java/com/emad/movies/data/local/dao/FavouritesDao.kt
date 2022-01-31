package com.emad.movies.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emad.movies.data.local.entities.FavouriteEntity

@Dao
interface FavouritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavourite(favouriteMovie: FavouriteEntity): Long

    @Query("select * from favouritestable")
    suspend fun getFavourites(): List<FavouriteEntity>

    @Query("DELETE from favouritestable")
    suspend fun clearDB()
}