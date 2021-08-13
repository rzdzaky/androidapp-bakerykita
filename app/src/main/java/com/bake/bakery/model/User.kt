package com.bake.bakery.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val username: String,
    val password: String,
    val isAdmin: Boolean = false
) : Parcelable