package com.emad.movies.domain.listeners

import com.emad.movies.data.model.PopularMovies

interface OnMovieSelected {
    fun movieSelected(movie: PopularMovies.Result)
}