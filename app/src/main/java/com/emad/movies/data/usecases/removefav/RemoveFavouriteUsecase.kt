package com.emad.movies.data.usecases.removefav

interface RemoveFavouriteUsecase {
    suspend operator fun invoke(movieID: Int): Int
}