package com.github.clockworkclyde.domain.model.product

data class ProductCard(
   override val category: String,
   override val name: String,
   override val price: Double,
   override val imageUrl: String?
): BaseProductCard() {
   override val itemId: Long = this.hashCode().toLong()
}