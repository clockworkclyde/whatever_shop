package com.github.clockworkclyde.domain.model.order

import com.github.clockworkclyde.domain.model.product.BaseProductCard

data class OrderProduct(
   val item: BaseProductCard? = null,
   val name: String,
   val color: String,
   val addedTimeInMs: Long,
)