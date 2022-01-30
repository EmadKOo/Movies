package com.emad.movies.data.usecases.requestrate

import com.emad.movies.data.model.RateResponse
import com.emad.movies.data.model.RequestRate
import com.emad.movies.data.repositories.MoviesRepository
import javax.inject.Inject

class RequestRateUsecaseImpl @Inject constructor(private val repository: MoviesRepository) :
    RequestRateUsecase {
    override suspend fun invoke(
        movie_id: Int,
        session_id: String,
        requestRate: RequestRate,
    ): RateResponse = repository.addRate(movie_id, session_id, requestRate)
}