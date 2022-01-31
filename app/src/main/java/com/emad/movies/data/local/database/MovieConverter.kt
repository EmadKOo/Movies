package com.emad.movies.data.local.database

import androidx.room.TypeConverter
import com.emad.movies.data.local.entities.MovieEntity
import com.google.gson.reflect.TypeToken

import com.google.gson.Gson
import java.lang.reflect.Type


interface MovieConverter {
    @TypeConverter
    fun fromMoviesList(countryLang: List<MovieEntity?>?): String? {
        if (countryLang == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<MovieEntity?>?>() {}.type
        return gson.toJson(countryLang, type)
    }

    @TypeConverter
    fun toMoviesList(countryLangString: String?): List<MovieEntity>? {
        if (countryLangString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<MovieEntity?>?>() {}.type
        return gson.fromJson<List<MovieEntity>>(countryLangString, type)
    }
}