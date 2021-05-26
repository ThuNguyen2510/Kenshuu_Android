package com.example.kenshuu.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Role (
    val authorityId: Int = 0,
    val authorityName: String? = null,
    val createUserId: String? = null,
    val updateUserId: String? = null,
    val createDate: Long = 0,
    val updateDate: Long = 0
): Parcelable