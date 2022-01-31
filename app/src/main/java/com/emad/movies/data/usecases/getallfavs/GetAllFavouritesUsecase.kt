package com.emad.movies.data.usecases.getallfavs

import com.emad.movies.data.local.entities.FavouriteEntity

interface GetAllFavouritesUsecase {
    suspend operator fun invoke(): ArrayList<FavouriteEntity>
}