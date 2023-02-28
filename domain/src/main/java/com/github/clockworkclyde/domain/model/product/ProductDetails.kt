package com.github.clockworkclyde.domain.model.product

data class ProductDetails(
   val name: String,
   val description: String,
   val rating: Double,
   val reviewsCount: Int,
   val price: Int,
   val colors: List<String>,
   val imageUrls: List<String>
)