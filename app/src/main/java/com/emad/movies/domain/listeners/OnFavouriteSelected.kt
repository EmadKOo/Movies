package com.emad.movies.domain.listeners

import com.emad.movies.data.local.entities.FavouriteEntity

interface OnFavouriteSelected {
    fun onFavSelected(favouriteEntity: FavouriteEntity)
}