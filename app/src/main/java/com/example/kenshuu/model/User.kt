package com.example.kenshuu.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.sql.ClientInfoStatus

@Parcelize
data class User (
    val status: String? = null,
    val token: String? = null,
    val familyName: String? = null,
    val data: DtUser? = null
) : Parcelable

@Parcelize
data class DtUser (
    var userId: String? = null,
    var password: String? = null,
    val familyName: String? = null,
    val firstName: String? = null,
    val age: Int? =null,
    val authorityId: Int? =null,
    val genderId: Int? =null,
    val createDate: Long? = null,
    val updateDate: Long? = null,
    val createUserId: String? = null,
    val updateUserId: String? = null
) : Parcelable
