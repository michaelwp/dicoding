package com.example.footballleague.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataLeague (
    val name: String,
    val image: Int,
    val desc: String
): Parcelable