package com.tech.androidchallenge.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], exportSchema = false, version = 1)
abstract class MyDatabase: RoomDatabase() {
    abstract fun userDao(): UsersDao
}