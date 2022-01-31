package com.emad.movies.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.emad.movies.data.local.dao.FavouritesDao
import com.emad.movies.data.local.entities.FavouriteEntity

@Database(
    entities = [FavouriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun favouritesDao(): FavouritesDao
}