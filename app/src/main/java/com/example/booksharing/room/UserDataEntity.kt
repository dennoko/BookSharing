package com.example.booksharing.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val UserName: String,
    val Organization: String,
)
