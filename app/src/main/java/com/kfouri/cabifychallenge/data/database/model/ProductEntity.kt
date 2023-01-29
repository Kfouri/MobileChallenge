package com.kfouri.cabifychallenge.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity(
    @PrimaryKey
    val id: Long = System.currentTimeMillis(),
    val code: String,
    val name: String,
    val amount: Int,
    val price: Float,
    val total: Float,
    val paid: Boolean = false
)