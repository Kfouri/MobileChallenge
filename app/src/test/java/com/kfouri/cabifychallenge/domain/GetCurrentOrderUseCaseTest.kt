package com.kfouri.cabifychallenge.domain

import com.kfouri.cabifychallenge.data.FakeRepository
import com.kfouri.cabifychallenge.domain.model.ProductModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetCurrentOrderUseCaseTest {

    private lateinit var getCurrentOrderUseCase: GetCurrentOrderUseCase
    private lateinit var getDiscountUseCase: GetDiscountUseCase
    private lateinit var addProductOrderUseCase: AddProductOrderUseCase

    private lateinit var fakeRepository: FakeRepository

    @Before
    fun setUp() {
        fakeRepository = FakeRepository()

        getDiscountUseCase = GetDiscountUseCase()
        getCurrentOrderUseCase = GetCurrentOrderUseCase(fakeRepository, getDiscountUseCase)
        addProductOrderUseCase = AddProductOrderUseCase(fakeRepository)
    }

    @Test
    fun `Add MUG product without discount, check total`() = runBlocking {

        val productModel = ProductModel(
            id = 0,
            code = "MUG",
            name = "CABIFY MUG",
            image = "https://",
            price = 7.5f,
            discount = null,
            amount = 1,
            total = 0f
        )

        addProductOrderUseCase.invoke(productModel)
        val currentOrder = getCurrentOrderUseCase.invoke().first()

        assert(currentOrder.total == 7.5f)

    }

    @Test
    fun `Add VOUCHER product with discount, check total`() = runBlocking {

        val productModel = ProductModel(
            id = 0,
            code = "VOUCHER",
            name = "CABIFY VOUCHER",
            image = "https://",
            price = 5f,
            discount = null,
            amount = 3,
            total = 0f
        )

        addProductOrderUseCase.invoke(productModel)
        val currentOrder = getCurrentOrderUseCase.invoke().first()

        assert(currentOrder.total == 10f)
    }

    @Test
    fun `Add T-SHIRT product with discount, check total`() = runBlocking {

        val productModel = ProductModel(
            id = 0,
            code = "TSHIRT",
            name = "CABIFY T-SHIRT",
            image = "https://",
            price = 20f,
            discount = null,
            amount = 4,
            total = 0f
        )

        addProductOrderUseCase.invoke(productModel)
        val currentOrder = getCurrentOrderUseCase.invoke().first()

        assert(currentOrder.total == 76f)
    }

    @Test
    fun `Add 4 T-SHIRT, 2 MUG and 3 VOUCHER products, check total`() = runBlocking {

        val tshirt = ProductModel(
            id = 0,
            code = "TSHIRT",
            name = "CABIFY T-SHIRT",
            image = "https://",
            price = 20f,
            discount = null,
            amount = 4,
            total = 0f
        )

        addProductOrderUseCase.invoke(tshirt)

        val mug = ProductModel(
            id = 0,
            code = "MUG",
            name = "CABIFY MUG",
            image = "https://",
            price = 7.5f,
            discount = null,
            amount = 2,
            total = 0f
        )

        addProductOrderUseCase.invoke(mug)

        val voucher = ProductModel(
            id = 0,
            code = "VOUCHER",
            name = "CABIFY VOUCHER",
            image = "https://",
            price = 5f,
            discount = null,
            amount = 3,
            total = 0f
        )

        addProductOrderUseCase.invoke(voucher)


        val currentOrder = getCurrentOrderUseCase.invoke().first()

        assert(currentOrder.total == 101f)
    }
}