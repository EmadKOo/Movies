package com.emad.movies.data.usecases.requestrate

import com.emad.movies.data.model.RateResponse
import com.emad.movies.data.model.RequestRate

interface RequestRateUsecase {
    suspend operator fun invoke(movie_id: Int, session_id: String, requestRate: RequestRate): RateResponse
}