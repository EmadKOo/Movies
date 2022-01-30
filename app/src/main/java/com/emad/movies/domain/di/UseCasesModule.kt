package com.emad.movies.domain.di

import com.emad.movies.data.repositories.MoviesRepository
import com.emad.movies.data.usecases.moviedetails.MovieDetailsUsecase
import com.emad.movies.data.usecases.moviedetails.MovieDetailsUsecaseImpl
import com.emad.movies.data.usecases.popularmovies.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UseCasesModule {
    @Singleton
    @Provides
    fun providePopularMovies(moviesRepository: MoviesRepository): PopularMoviesUseCase =
        PopularMoviesUseCaseImpl(moviesRepository)

    @Singleton
    @Provides
    fun provideMovieDetails(moviesRepository: MoviesRepository): MovieDetailsUsecase =
        MovieDetailsUsecaseImpl(moviesRepository)

}