package com.github.clockworkclyde.data.models.dto

import com.google.gson.annotations.SerializedName

data class DiscountProductsResponse(
   @SerializedName("flash_sale") val items: List<ProductCardDiscountDto>
)
