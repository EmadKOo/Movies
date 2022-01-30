package com.emad.movies.data.usecases.getmoviereviews

import com.emad.movies.data.model.MovieReviews

interface GetMovieReviewsUsecase {
    suspend operator fun invoke(movieID: Int): MovieReviews
}