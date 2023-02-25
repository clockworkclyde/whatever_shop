package com.github.clockworkclyde.eshop.ui.categories.model

data class ProductCard(
   override val category: String,
   override val name: String,
   override val price: Double,
   override val image: String?
): BaseProductCard() {
   override val itemId: Long = this.hashCode().toLong()
}