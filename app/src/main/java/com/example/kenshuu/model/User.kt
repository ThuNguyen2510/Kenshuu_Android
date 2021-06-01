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
    val data: List<DtUser>? = null
) : Parcelable

@Parcelize
data class DtUser (
    val userId: String? = "",
    val password: String? = "",
    val familyName: String? = "",
    val firstName: String? = "",
    val age: Int? =0,
    val admin: Int? =0,
    val authorityId: Int? =0,
    val genderId: Int? =0,
    val createDate: Long? = null,
    val updateDate: Long? = null,
    val createUserId: String? = null,
    val updateUserId: String? = null,
    val role : Role?=null,
    val gender : Gender? = null
) : Parcelable
