package com.github.clockworkclyde.providers.remote.api

import com.github.clockworkclyde.data.models.dto.DiscountProductsResponse
import com.github.clockworkclyde.data.models.dto.LatestProductsResponse
import retrofit2.http.GET

interface EShopApi {

   @GET("cc0071a1-f06e-48fa-9e90-b1c2a61eaca7")
   suspend fun getLatestProducts(): LatestProductsResponse

   @GET("a9ceeb6e-416d-4352-bde6-2203416576ac")
   suspend fun getDiscountProducts(): DiscountProductsResponse

}