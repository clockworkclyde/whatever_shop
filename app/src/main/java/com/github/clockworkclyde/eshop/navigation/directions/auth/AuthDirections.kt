package com.github.clockworkclyde.eshop.navigation.directions.auth

import com.github.clockworkclyde.eshop.R
import com.github.clockworkclyde.core.navigation.directions.INavDirections
import com.github.clockworkclyde.core.navigation.directions.INavEvent

class AuthDirections
   : INavDirections {

   fun showLogin() = INavEvent.ShowScreen(R.id.action_signInFragment_to_loginFragment)

   fun signInToShopCategoriesCleared() = INavEvent.ShowScreen(
      id = R.id.action_signInFragment_to_categoriesFragment,
      popUpTo = R.id.signInFragment,
      inclusive = true
   )

   fun loginToShopCategoriesCleared() = INavEvent.ShowScreen(
      id = R.id.action_loginFragment_to_categoriesFragment,
      popUpTo = R.id.signInFragment,
      inclusive = true
   )
}