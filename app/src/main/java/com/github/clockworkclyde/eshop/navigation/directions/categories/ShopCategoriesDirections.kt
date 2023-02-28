package com.github.clockworkclyde.eshop.navigation.directions.categories

import com.github.clockworkclyde.core.navigation.directions.INavDirections
import com.github.clockworkclyde.core.navigation.directions.INavEvent
import com.github.clockworkclyde.eshop.R

class ShopCategoriesDirections
   : INavDirections {

      fun rootToDetails() = INavEvent.ShowScreen(R.id.action_categoriesFragment_to_productDetailsFragment)

      fun rootToCategory() = Unit

      fun rootToProfile() = INavEvent.ShowScreen(R.id.action_categoriesFragment_to_profileFragment)
}