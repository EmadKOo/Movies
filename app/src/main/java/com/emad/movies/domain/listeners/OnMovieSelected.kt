package com.emad.movies.domain.listeners

import com.emad.movies.data.local.entities.MovieEntity
import com.emad.movies.data.model.PopularMovies

interface OnMovieSelected {
    fun movieSelected(movie: MovieEntity)
}