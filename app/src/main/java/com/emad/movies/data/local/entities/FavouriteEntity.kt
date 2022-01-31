package com.emad.movies.data.local.entities

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.emad.movies.utils.Constants.Companion.FAVOURITES_TABLE
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
@Entity(tableName = FAVOURITES_TABLE, indices = [Index(value = ["movieID"], unique = true)])
data class FavouriteEntity(
    @PrimaryKey
    val movieID: Int,
    val movieName: String,
    val movieImagePath: String,
) : Parcelable
