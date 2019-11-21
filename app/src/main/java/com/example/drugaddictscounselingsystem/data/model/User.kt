package com.example.drugaddictscounselingsystem.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(val id: String, val name: String, val photoUri: String) : Parcelable {


    constructor() : this("", "", "")

    fun toMap(): Map<String, Any> {
        return mapOf(
                "uid" to id,
                "name" to name,
                "profileUri" to photoUri
        )
    }
}

