package com.github.clockworkclyde.providers.local.pref.cart

import com.chibatching.kotpref.KotprefModel

object ShoppingCartPref: KotprefModel() {
   val items by stringSetPref()
}