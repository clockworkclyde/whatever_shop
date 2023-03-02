package com.github.clockworkclyde.providers.local.pref.product

import com.chibatching.kotpref.KotprefModel

object ProductPref: KotprefModel() {
   val favorites by stringSetPref()
}