package com.kfouri.cabifychallenge.domain

import com.kfouri.cabifychallenge.data.FakeRepository
import com.kfouri.cabifychallenge.data.model.Resource
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetProductListUseCaseTest {


    private lateinit var getProductListUseCase: GetProductListUseCase
    private lateinit var fakeRepository: FakeRepository
    private lateinit var getDiscountUseCase: GetDiscountUseCase

    @Before
    fun setUp() {
        fakeRepository = FakeRepository()
        getDiscountUseCase = GetDiscountUseCase()

        getProductListUseCase = GetProductListUseCase(
            fakeRepository,
            getDiscountUseCase
        )
    }

    @Test
    fun `Get Products, check Success`() = runBlocking {

        val productResource = getProductListUseCase.invoke()

        assert(productResource is Resource.Success)
    }

    @Test
    fun `Get Products from network, check Success`() = runBlocking {

        val productResource = getProductListUseCase.invoke()

        assert(productResource is Resource.Success)
    }
}