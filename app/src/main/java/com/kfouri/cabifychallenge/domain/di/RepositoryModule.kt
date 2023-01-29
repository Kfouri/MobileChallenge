package com.kfouri.cabifychallenge.domain.di

import com.kfouri.cabifychallenge.data.ProductRepositoryImpl
import com.kfouri.cabifychallenge.data.client.ProductService
import com.kfouri.cabifychallenge.data.database.ProductDao
import com.kfouri.cabifychallenge.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providesProductRepository(
        productService: ProductService,
        productDao: ProductDao,
    ): ProductRepository {
        return ProductRepositoryImpl(
            productService,
            productDao
        )
    }
}