package com.emad.movies.domain.di

import com.emad.movies.data.repositories.MoviesRepository
import com.emad.movies.data.usecases.addfavourite.AddFavouriteUsecase
import com.emad.movies.data.usecases.addfavourite.AddFavouriteUsecaseImpl
import com.emad.movies.data.usecases.checkfav.CheckMovieUsecase
import com.emad.movies.data.usecases.checkfav.CheckMovieUsecaseImpl
import com.emad.movies.data.usecases.getallfavs.GetAllFavouritesUsecase
import com.emad.movies.data.usecases.getallfavs.GetAllFavouritesUsecaseImpl
import com.emad.movies.data.usecases.getmoviereviews.GetMovieReviewsUsecase
import com.emad.movies.data.usecases.getmoviereviews.GetMovieReviewsUsecaseImpl
import com.emad.movies.data.usecases.moviedetails.MovieDetailsUsecase
import com.emad.movies.data.usecases.moviedetails.MovieDetailsUsecaseImpl
import com.emad.movies.data.usecases.popularmovies.*
import com.emad.movies.data.usecases.requestrate.RequestRateUsecase
import com.emad.movies.data.usecases.requestrate.RequestRateUsecaseImpl
import com.emad.movies.data.usecases.requesttoken.RequestTokenUsecase
import com.emad.movies.data.usecases.requesttoken.RequestTokenUsecaseImpl
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

    @Singleton
    @Provides
    fun provideMovieReviews(moviesRepository: MoviesRepository): GetMovieReviewsUsecase =
        GetMovieReviewsUsecaseImpl(moviesRepository)

    @Singleton
    @Provides
    fun provideRequestingToken(moviesRepository: MoviesRepository): RequestTokenUsecase =
        RequestTokenUsecaseImpl(moviesRepository)

    @Singleton
    @Provides
    fun provideAddingRate(moviesRepository: MoviesRepository): RequestRateUsecase =
        RequestRateUsecaseImpl(moviesRepository)

    @Singleton
    @Provides
    fun provideAddingFavourite(moviesRepository: MoviesRepository): AddFavouriteUsecase =
        AddFavouriteUsecaseImpl(moviesRepository)

    @Singleton
    @Provides
    fun provideGetAllFavourites(moviesRepository: MoviesRepository): GetAllFavouritesUsecase =
        GetAllFavouritesUsecaseImpl(moviesRepository)

    @Singleton
    @Provides
    fun provideCheckingIfMovieIsFav(moviesRepository: MoviesRepository): CheckMovieUsecase =
        CheckMovieUsecaseImpl(moviesRepository)

}