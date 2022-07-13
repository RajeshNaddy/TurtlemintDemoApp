package com.turtlemint.assignment.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val avatar_url: String,
    val id: Int,
    val login: String
):Parcelable