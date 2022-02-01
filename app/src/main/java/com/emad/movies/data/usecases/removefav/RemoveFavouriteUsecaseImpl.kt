package com.emad.movies.data.usecases.removefav

import com.emad.movies.data.repositories.MoviesRepository
import javax.inject.Inject

class RemoveFavouriteUsecaseImpl @Inject constructor(private val repository: MoviesRepository): RemoveFavouriteUsecase{
    override suspend fun invoke(movieID: Int): Int = repository.unFavMovie(movieID)
}