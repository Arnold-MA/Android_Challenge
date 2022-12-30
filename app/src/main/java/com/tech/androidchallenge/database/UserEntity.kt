package com.tech.androidchallenge.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class UserEntity(
    @PrimaryKey val username: String,
    val password: String,
    val name: String,
    val lastname: String,
    //val email: String
)