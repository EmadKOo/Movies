package com.emad.movies.data.usecases.requesttoken

import com.emad.movies.data.model.Token

interface RequestTokenUsecase {
    suspend operator fun invoke(): Token
}