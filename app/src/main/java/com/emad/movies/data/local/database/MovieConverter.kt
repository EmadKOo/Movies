package com.emad.movies.data.local.database

import androidx.room.TypeConverter
import com.emad.movies.data.local.entities.MovieEntity
import com.emad.movies.data.local.entities.ReviewEntity
import com.google.gson.reflect.TypeToken

import com.google.gson.Gson
import java.lang.reflect.Type


interface MovieConverter {
    @TypeConverter
    fun fromMoviesList(movie: List<MovieEntity?>?): String? {
        if (movie == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<MovieEntity?>?>() {}.type
        return gson.toJson(movie, type)
    }

    @TypeConverter
    fun toMoviesList(movie: String?): List<MovieEntity>? {
        if (movie == null)
            return null

        val gson = Gson()
        val type: Type = object : TypeToken<List<MovieEntity?>?>() {}.type
        return gson.fromJson<List<MovieEntity>>(movie, type)
    }

    @TypeConverter
    fun fromReviewsList(review: List<ReviewEntity?>?): String? {
        if (review == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ReviewEntity?>?>() {}.type
        return gson.toJson(review, type)
    }

    @TypeConverter
    fun toReviewsList(review: String?): List<ReviewEntity>? {
        if (review == null)
            return null

        val gson = Gson()
        val type: Type = object : TypeToken<List<ReviewEntity?>?>() {}.type
        return gson.fromJson<List<ReviewEntity>>(review, type)
    }
}