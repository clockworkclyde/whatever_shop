package com.github.clockworkclyde.providers.remote.api

import com.github.clockworkclyde.data.models.dto.DiscountProductsResponse
import com.github.clockworkclyde.data.models.dto.LatestProductsResponse
import com.github.clockworkclyde.data.models.dto.ProductDetailsDto
import com.github.clockworkclyde.data.models.dto.SearchSuggestionsResponse
import retrofit2.http.GET

interface EShopApi {

   @GET("cc0071a1-f06e-48fa-9e90-b1c2a61eaca7")
   suspend fun getLatestProducts(): LatestProductsResponse

   @GET("a9ceeb6e-416d-4352-bde6-2203416576ac")
   suspend fun getDiscountProducts(): DiscountProductsResponse

   @GET("4c9cd822-9479-4509-803d-63197e5a9e19")
   suspend fun getSearchSuggestions(): SearchSuggestionsResponse

   @GET("f7f99d04-4971-45d5-92e0-70333383c239")
   suspend fun getProductDetails(): ProductDetailsDto
}