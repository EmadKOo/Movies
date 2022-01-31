package com.emad.movies.data.usecases.addfavourite

import com.emad.movies.data.local.entities.FavouriteEntity

interface AddFavouriteUsecase {
    suspend operator fun invoke(favouriteEntity: FavouriteEntity): Long
}