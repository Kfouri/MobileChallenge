package com.kfouri.cabifychallenge.data.client

import com.kfouri.cabifychallenge.data.network.response.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductClient {

    //@GET("palcalde/6c19259bd32dd6aafa327fa557859c2f/raw/ba51779474a150ee4367cda4f4ffacdcca479887/Products.json")
    @GET("Kfouri/c39796077266b96d2090586b447e624b/raw/5fc46e608608c7c33b18e2575694bbf79868b807/products.json")
    suspend fun getProducts(): Response<ProductsResponse>

}