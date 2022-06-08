package com.bignerdranch.android.starwars.domain.entities
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cast(
    val id: Int,
    val name: String,
    val profile_path: String?,
    val original_name : String
): Parcelable