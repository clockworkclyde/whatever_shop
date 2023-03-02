package com.github.clockworkclyde.data.models.dto

import android.os.Parcelable
import com.github.clockworkclyde.core.common.IConvertableTo
import com.github.clockworkclyde.domain.model.product.ProductCardDiscount
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductCardDiscountDto(
   @SerializedName("category") val category: String,
   @SerializedName("name") val name: String,
   @SerializedName("price") val price: Double,
   @SerializedName("discount") val discount: Int,
   @SerializedName("image_url") val imageUrl: String
) : Parcelable, IConvertableTo<ProductCardDiscount> {

   override fun convertTo(): ProductCardDiscount {
      return ProductCardDiscount(
         category = category, name = name, price = price, imageUrl = imageUrl, discount = discount
      )
   }
}