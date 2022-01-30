package com.emad.movies.data.usecases.requesttoken

import com.emad.movies.data.model.Token
import com.emad.movies.data.repositories.MoviesRepository
import javax.inject.Inject

class RequestTokenUsecaseImpl @Inject constructor(private val repository: MoviesRepository): RequestTokenUsecase {
    override suspend fun invoke(): Token = repository.requestNewToken()
}