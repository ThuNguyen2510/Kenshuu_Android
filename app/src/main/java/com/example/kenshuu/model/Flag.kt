package com.example.kenshuu.model

import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
@Parcelize
data class Flag (
    val status: String? = "",
    val message: String? = ""
): Parcelable