package com.example.kenshuu.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Count (
    val authorityName: String? = null,
    val male : Int = 0,
    val female : Int= 0,
    val nullGender : Int = 0,
    val under19 : Int= 0,
    val over20 : Int= 0,
    val nullAge : Int= 0
): Parcelable