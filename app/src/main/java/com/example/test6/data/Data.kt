package com.example.test6.data

import androidx.annotation.DrawableRes

data class Data(
    val number: Int,
    val type: Type,
    @DrawableRes val imageRes: Int = 0
)

enum class Type {
    NUMBER,
    FINGERPRINT,
    DELETE
}