package com.tech.androidchallenge.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductsDao {
    @Insert
    suspend fun insert(product: ProductEntity)

    @Update
    suspend fun update(product: ProductEntity): Int

    @Delete
    suspend fun delete(product: ProductEntity)

    @Query("SELECT * FROM products WHERE status = :status")
    suspend fun getProductsByStatus(status: Boolean): List<ProductEntity>

    @Query("SELECT * FROM products WHERE productId = :code")
    suspend fun getProduct(code: String): ProductEntity?

    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<ProductEntity>

    @Query("SELECT * FROM products WHERE productId = :productId")
    suspend fun verifyProduct(productId:String): ProductEntity?
}