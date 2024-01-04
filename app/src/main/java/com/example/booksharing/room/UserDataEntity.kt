package com.example.booksharing.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDataEntity(
    // データは1つなので、id は 0 で固定
    @PrimaryKey val id: Int = 0,
    val userName: String? = null,
    val organization: String = "C0de",
)
