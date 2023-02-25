package com.github.clockworkclyde.eshop.ui.categories.model

import com.github.clockworkclyde.core.presentation.adapters.ListItem

abstract class BaseProductCard: ListItem {
   abstract val category: String
   abstract val name: String
   abstract val price: Double
   abstract val image: String?
}