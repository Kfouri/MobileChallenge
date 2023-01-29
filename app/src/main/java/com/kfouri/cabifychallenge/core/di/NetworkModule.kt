package com.kfouri.cabifychallenge.core.di

import com.kfouri.cabifychallenge.data.client.ProductClient
import com.kfouri.cabifychallenge.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideProductClient(retrofit: Retrofit): ProductClient {
        return retrofit.create(ProductClient::class.java)
    }
}