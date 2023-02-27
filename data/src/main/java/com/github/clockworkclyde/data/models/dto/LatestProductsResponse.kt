package com.github.clockworkclyde.data.models.dto

import com.google.gson.annotations.SerializedName

data class LatestProductsResponse(
   @SerializedName("latest") val items: List<ProductCardDto>
)
