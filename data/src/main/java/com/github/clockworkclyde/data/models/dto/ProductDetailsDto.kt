package com.github.clockworkclyde.data.models.dto

import com.github.clockworkclyde.core.common.IConvertableTo
import com.github.clockworkclyde.domain.model.product.ProductDetails
import com.github.clockworkclyde.domain.model.product.ProductPhoto
import com.google.gson.annotations.SerializedName

data class ProductDetailsDto(
   @SerializedName("name") val name: String,
   @SerializedName("description") val description: String,
   @SerializedName("rating") val rating: Double,
   @SerializedName("number_of_reviews") val reviewsCount: Int,
   @SerializedName("price") val price: Int,
   @SerializedName("colors") val colors: List<String>,
   @SerializedName("image_urls") val imageUrls: List<String>
): IConvertableTo<ProductDetails> {

   override fun convertTo(): ProductDetails {
      return ProductDetails(
         name = name,
         description = description,
         rating = rating,
         reviewsCount = reviewsCount,
         price = price,
         colors = colors,
         imageUrls = imageUrls.map { ProductPhoto(it) }
      )
   }
}