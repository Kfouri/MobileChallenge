package com.kfouri.cabifychallenge.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kfouri.cabifychallenge.data.database.model.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class ProductsDatabase: RoomDatabase() {

    abstract fun productDao(): ProductDao

}