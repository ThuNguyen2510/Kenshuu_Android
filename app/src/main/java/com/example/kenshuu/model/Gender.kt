package com.example.kenshuu.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Gender (
    val genderId: Int = 0,
    val genderName: String? = null,
    val createUserId: String? = null,
    val updateUserId: String? = null,
    val createDate: Long = 0,
    val updateDate: Long = 0
): Parcelable