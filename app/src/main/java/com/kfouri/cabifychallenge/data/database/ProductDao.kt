package com.kfouri.cabifychallenge.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kfouri.cabifychallenge.data.database.model.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM ProductEntity where paid = 0")
    fun getProducts(): Flow<List<ProductEntity>>

    @Insert
    suspend fun addProduct(productEntity: ProductEntity)

    @Query("UPDATE ProductEntity SET paid = 1")
    suspend fun buyProducts()

    @Query("DELETE FROM ProductEntity WHERE code = :code AND paid = 0")
    suspend fun removeProductOrder(code: String): Int
}