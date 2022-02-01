package com.emad.movies.data.local.entities

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.emad.movies.utils.Constants
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
@Entity(tableName = Constants.DETAILS_TABLE, indices = [Index(value = ["movieID"], unique = true)])
data class DetailsEntity(
    @PrimaryKey
    val movieID: Int,
    val movieName: String,
    val movieImagePath: String,
    val movieDescription: String,
    val movieReleaseDate: String,
    val movieVotes: Double,
    val moviePopularity: Double,
) : Parcelable

