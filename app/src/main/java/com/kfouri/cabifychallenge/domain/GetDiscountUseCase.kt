package com.kfouri.cabifychallenge.domain

import com.kfouri.cabifychallenge.domain.model.Discount
import com.kfouri.cabifychallenge.domain.model.PriceDiscount
import com.kfouri.cabifychallenge.util.Constants.ON_SALE_2X1
import com.kfouri.cabifychallenge.util.Constants.ON_SALE_DISCOUNT_MORE_THAN_THREE
import com.kfouri.cabifychallenge.util.Constants.ON_SALE_NONE
import com.kfouri.cabifychallenge.util.Constants.TSHIRT
import com.kfouri.cabifychallenge.util.Constants.VOUCHER
import javax.inject.Inject
import kotlin.math.roundToInt

class GetDiscountUseCase @Inject constructor() {

    private val discount2x1 = Discount(ON_SALE_2X1, 0f, "It has 2x1 discount")
    private val discountMoreThanThree = Discount(ON_SALE_DISCOUNT_MORE_THAN_THREE, 19f, "It has > 3 discount")
    private val discountNone = Discount(ON_SALE_NONE, 0f, "Without discount")

    private val discountList = mapOf(
        VOUCHER to discount2x1,
        TSHIRT to discountMoreThanThree
    )

    fun applyDiscount(code: String): Discount {
        return discountList[code] ?: run { discountNone }
    }

    fun getPriceDiscount(productCode: String, productPrice: Float, amount: Int): PriceDiscount {
        val discount = applyDiscount(productCode)
        return if (discount.type != ON_SALE_NONE) {
            when (discount.type) {
                ON_SALE_2X1 -> {
                    PriceDiscount(
                        price = productPrice,
                        total = (amount / 2.0).roundToInt() * productPrice
                    )
                }
                ON_SALE_DISCOUNT_MORE_THAN_THREE -> {
                    if (productCode == TSHIRT) {
                        if (amount >= 3) {
                            PriceDiscount(
                                price = discountMoreThanThree.price,
                                total = discountMoreThanThree.price * amount
                            )
                        } else {
                            PriceDiscount(
                                price = productPrice,
                                total = productPrice * amount
                            )
                        }
                    } else {
                        PriceDiscount(price = 0f, total = 0f)
                    }
                }
                else -> {
                    PriceDiscount(price = 0f, total = 0f)
                }
            }
        } else {
            PriceDiscount(price = productPrice, total = productPrice * amount)
        }
    }
}