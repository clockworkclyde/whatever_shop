package com.github.clockworkclyde.data.models.local

import android.os.Parcelable
import com.github.clockworkclyde.core.common.IConvertableTo
import com.github.clockworkclyde.domain.model.order.OrderProduct
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShoppingCartEntity(
   val name: String,
   val color: String,
   val addedTimeInMs: Long,
) : IConvertableTo<OrderProduct>, Parcelable {

   override fun convertTo(): OrderProduct {
      return OrderProduct(
         name = name,
         color = color,
         addedTimeInMs = addedTimeInMs
      )
   }

   companion object {
      fun OrderProduct.toCartEntity(): ShoppingCartEntity {
         return ShoppingCartEntity(
            name = name,
            color = color,
            addedTimeInMs = addedTimeInMs
         )
      }
   }
}