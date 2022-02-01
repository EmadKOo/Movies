package com.emad.movies.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Embedded
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class PopularMovies(
    val page: Int,
    @Embedded
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int,
) : Parcelable {
    @Keep
    @Parcelize
    data class Result(
        val adult: Boolean,
        val backdrop_path: String,
        val genre_ids: List<Int>,
        val id: Int,
        val original_language: String,
        val original_title: String,
        val overview: String,
        val popularity: Double,
        val poster_path: String,
        val release_date: String,
        val title: String,
        val video: Boolean,
        val vote_average: Double,
        val vote_count: Int,
    ) : Parcelable
}