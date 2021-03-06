package com.emad.movies.domain.di

import android.app.Application
import androidx.room.Room
import com.emad.movies.data.local.dao.DetailsDao
import com.emad.movies.data.local.dao.FavouritesDao
import com.emad.movies.data.local.dao.MovieDao
import com.emad.movies.data.local.dao.ReviewsDao
import com.emad.movies.data.local.database.MovieDatabase
import com.emad.movies.utils.Constants.Companion.MOVIE_DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideRoomDB( app: Application): MovieDatabase {
        return Room.databaseBuilder(app, MovieDatabase::class.java, MOVIE_DB)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun providesFavouritesDao(db: MovieDatabase): FavouritesDao {
        return db.favouritesDao()
    }

    @Provides
    @Singleton
    fun providesMoviesDao(db: MovieDatabase): MovieDao {
        return db.movieDao()
    }

    @Provides
    @Singleton
    fun providesDetailsDao(db: MovieDatabase): DetailsDao {
        return db.movieDetails()
    }

    @Provides
    @Singleton
    fun providesReviewsDao(db: MovieDatabase): ReviewsDao {
        return db.reviewsDao()
    }
}