package com.emad.movies.data.usecases.moviedetails

import com.emad.movies.data.local.entities.DetailsEntity
import com.emad.movies.data.model.MovieDetails
import com.emad.movies.data.repositories.MoviesRepository
import javax.inject.Inject

class MovieDetailsUsecaseImpl @Inject constructor(private val repository: MoviesRepository): MovieDetailsUsecase{
    override suspend fun invoke(movieID: Int): DetailsEntity = repository.getMovieDetails(movieID)
}