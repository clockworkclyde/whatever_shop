package com.github.clockworkclyde.eshop.navigation.directions.auth

import com.github.clockworkclyde.eshop.R
import com.github.clockworkclyde.core.navigation.directions.INavDirections
import com.github.clockworkclyde.core.navigation.directions.INavEvent

class SignInDirections(
) : INavDirections {

   fun showLogin() = INavEvent.ShowScreen(R.id.action_signInFragment_to_loginFragment)

}