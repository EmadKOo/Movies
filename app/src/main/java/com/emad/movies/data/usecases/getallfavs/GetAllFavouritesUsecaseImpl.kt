package com.emad.movies.data.usecases.getallfavs

import com.emad.movies.data.local.entities.FavouriteEntity
import com.emad.movies.data.repositories.MoviesRepository
import javax.inject.Inject

class GetAllFavouritesUsecaseImpl @Inject constructor(private val repository: MoviesRepository): GetAllFavouritesUsecase{
    override suspend fun invoke(): ArrayList<FavouriteEntity> = repository.getAllFavourites() as ArrayList<FavouriteEntity>

}