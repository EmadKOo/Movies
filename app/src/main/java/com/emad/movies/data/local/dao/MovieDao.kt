package com.emad.movies.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emad.movies.data.local.entities.MovieEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movieEntitys: List<MovieEntity>): Array<Long>

    @Query("select * from moviestable")
    suspend fun getMovies(): List<MovieEntity>

    @Query("DELETE from moviestable")
    suspend fun clearDB()
}