package com.github.clockworkclyde.data.models.dto

import android.os.Parcelable
import com.github.clockworkclyde.core.common.IConvertableTo
import com.github.clockworkclyde.domain.model.product.ProductCard
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductCardDto(
   @SerializedName("category") val category: String,
   @SerializedName("name") val name: String,
   @SerializedName("price") val price: Double,
   @SerializedName("image_url") val imageUrl: String
) : Parcelable, IConvertableTo<ProductCard> {

   override fun convertTo(): ProductCard {
      return ProductCard(
         category = category,
         name = name,
         price = price,
         imageUrl = imageUrl,
      )
   }
}