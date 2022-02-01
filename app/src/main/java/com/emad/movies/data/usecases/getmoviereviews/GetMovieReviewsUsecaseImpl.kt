package com.emad.movies.data.usecases.getmoviereviews

import com.emad.movies.data.local.entities.ReviewEntity
import com.emad.movies.data.model.MovieReviews
import com.emad.movies.data.repositories.MoviesRepository
import javax.inject.Inject

class GetMovieReviewsUsecaseImpl @Inject constructor(private val repository: MoviesRepository): GetMovieReviewsUsecase {
    override suspend fun invoke(movieID: Int): List<ReviewEntity> = repository.getMovieReviews(movieID)
}