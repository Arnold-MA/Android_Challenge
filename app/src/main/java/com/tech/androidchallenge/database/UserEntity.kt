package com.tech.androidchallenge.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val username: String,
    val password: String,
    val name: String,
    val lastname: String,
)