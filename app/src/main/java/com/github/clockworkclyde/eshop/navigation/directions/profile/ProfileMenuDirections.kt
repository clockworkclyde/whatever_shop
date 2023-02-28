package com.github.clockworkclyde.eshop.navigation.directions.profile

import com.github.clockworkclyde.core.navigation.directions.INavDirections
import com.github.clockworkclyde.core.navigation.directions.INavEvent
import com.github.clockworkclyde.eshop.R

class ProfileMenuDirections
   : INavDirections {

      fun onLogOut() = INavEvent.ShowScreen(
         id = R.id.action_to_signInFragment_cleared,
      )
}