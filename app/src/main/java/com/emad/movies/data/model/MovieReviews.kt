package com.emad.movies.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize
@Keep
@Parcelize
data class MovieReviews(
    val id: Int,
    val page: Int,
    val total_pages: Int,
    val total_results: Int,
    val results: ArrayList<Result>,
) : Parcelable {
    @Keep
    @Parcelize
    data class Result(
        val author: String,
        val content: String,
        val created_at: String,
        val id: String,
        val updated_at: String,
        val url: String,
        val author_details: AuthorDetails,
    ): Parcelable
    @Keep
    @Parcelize
    data class AuthorDetails(
        val name: String,
        val username: String,
        val avatar_path: String,
        val rating: Int,
    ): Parcelable
}
