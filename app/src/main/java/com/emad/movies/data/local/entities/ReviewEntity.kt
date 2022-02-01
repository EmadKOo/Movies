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
@Entity(tableName = Constants.REVIEW_TABLE, indices = [Index(value = ["movieID"], unique = true)])
data class ReviewEntity(
    @PrimaryKey
    val movieID: Int,
    val reviewID: Int,
    val authorName: String,
    val reviewContent: String
) : Parcelable