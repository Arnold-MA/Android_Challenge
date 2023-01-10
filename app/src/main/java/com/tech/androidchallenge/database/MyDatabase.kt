package com.tech.androidchallenge.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class, ProductEntity::class], exportSchema = false, version = 2)
abstract class MyDatabase: RoomDatabase() {
    abstract fun userDao(): UsersDao
    abstract fun productDao(): ProductsDao
}