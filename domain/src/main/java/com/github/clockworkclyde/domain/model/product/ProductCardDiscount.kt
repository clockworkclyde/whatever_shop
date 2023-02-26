package com.github.clockworkclyde.domain.model.product

data class ProductCardDiscount(
   override val category: String,
   override val name: String,
   override val price: Double,
   override val imageUrl: String?,
   val discount: Int
): BaseProductCard() {
   override val itemId: Long = this.hashCode().toLong()
}