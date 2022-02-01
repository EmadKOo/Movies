package com.emad.movies.data.usecases.moviedetails

import com.emad.movies.data.local.entities.DetailsEntity
import com.emad.movies.data.model.MovieDetails

interface MovieDetailsUsecase {
    suspend operator fun invoke(movieID: Int): DetailsEntity
}