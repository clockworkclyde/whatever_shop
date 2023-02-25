package com.github.clockworkclyde.eshop.ui.categories.model

data class ProductCardDiscount(
   override val category: String,
   override val name: String,
   override val price: Double,
   override val image: String?,
   val discount: Int
): BaseProductCard() {
   override val itemId: Long = this.hashCode().toLong()
}