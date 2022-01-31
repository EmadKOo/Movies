package com.emad.movies.data.usecases.addfavourite

import com.emad.movies.data.local.entities.FavouriteEntity
import com.emad.movies.data.repositories.MoviesRepository
import javax.inject.Inject

class AddFavouriteUsecaseImpl @Inject constructor(private val repository: MoviesRepository): AddFavouriteUsecase {
    override suspend fun invoke(favouriteEntity: FavouriteEntity): Long = repository.addFavourite(favouriteEntity)
}