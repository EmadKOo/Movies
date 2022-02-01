package com.emad.movies.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.emad.movies.data.local.dao.DetailsDao
import com.emad.movies.data.local.dao.FavouritesDao
import com.emad.movies.data.local.dao.MovieDao
import com.emad.movies.data.local.dao.ReviewsDao
import com.emad.movies.data.local.entities.DetailsEntity
import com.emad.movies.data.local.entities.FavouriteEntity
import com.emad.movies.data.local.entities.MovieEntity
import com.emad.movies.data.local.entities.ReviewEntity

@Database(
    entities = [FavouriteEntity::class, MovieEntity::class, DetailsEntity::class, ReviewEntity::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(MovieConverter::class)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun favouritesDao(): FavouritesDao
    abstract fun movieDao(): MovieDao
    abstract fun movieDetails(): DetailsDao
    abstract fun reviewsDao(): ReviewsDao
}