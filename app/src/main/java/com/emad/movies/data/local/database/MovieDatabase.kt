package com.emad.movies.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.emad.movies.data.local.dao.FavouritesDao
import com.emad.movies.data.local.dao.MovieDao
import com.emad.movies.data.local.entities.FavouriteEntity
import com.emad.movies.data.local.entities.MovieEntity

@Database(
    entities = [FavouriteEntity::class, MovieEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(MovieConverter::class)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun favouritesDao(): FavouritesDao
    abstract fun movieDao(): MovieDao
}