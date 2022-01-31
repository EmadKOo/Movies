package com.emad.movies.data.usecases.checkfav

interface CheckMovieUsecase {
    suspend operator fun invoke(movieID: Int): Int
}