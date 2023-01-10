package com.tech.androidchallenge.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "products",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["username"],
        childColumns = ["user"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ProductEntity(
    @PrimaryKey val productId: String,
    val name: String,
    val description: String,
    val unit_price: Double,
    val status: Boolean,
    val user: String,
)