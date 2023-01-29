package com.kfouri.cabifychallenge.data.di

import android.content.Context
import androidx.room.Room
import com.kfouri.cabifychallenge.data.database.ProductsDatabase
import com.kfouri.cabifychallenge.data.database.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideProductDao(database: ProductsDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    @Singleton
    fun provideProductsDatabase(@ApplicationContext appContext: Context): ProductsDatabase {
        return Room.databaseBuilder(appContext, ProductsDatabase::class.java, "ProductsDatabase").build()
    }
}