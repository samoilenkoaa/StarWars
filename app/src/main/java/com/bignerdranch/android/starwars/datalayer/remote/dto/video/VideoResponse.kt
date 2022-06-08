package com.bignerdranch.android.starwars.datalayer.remote.dto.video
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoResponse (
    @SerializedName("id") val id : Int,
    @SerializedName("results") val results : List<VideoResults>
): Parcelable