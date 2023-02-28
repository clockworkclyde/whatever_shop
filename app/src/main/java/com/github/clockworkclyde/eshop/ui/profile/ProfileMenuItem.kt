package com.github.clockworkclyde.eshop.ui.profile

import com.github.clockworkclyde.eshop.R

sealed class ProfileMenuItem(
   val titleResId: Int,
   val iconResId: Int,
   val arrowVisibility: Boolean = true,
   val balance: Int? = null
) {

   object TradeStore :
      ProfileMenuItem(R.string.menu_trade_store, R.drawable.ic_credit_card)

   object PaymentMethod :
      ProfileMenuItem(R.string.menu_payment_method, R.drawable.ic_credit_card)

   class Balance(balance: Int? = 0) :
      ProfileMenuItem(
         R.string.menu_balance,
         R.drawable.ic_credit_card,
         arrowVisibility = false,
         balance = balance
      )

   object History :
      ProfileMenuItem(R.string.menu_trade_history, R.drawable.ic_credit_card)

   object RestorePurchase :
      ProfileMenuItem(R.string.menu_restore_purchase, R.drawable.ic_restore_purchase)

   object Help :
      ProfileMenuItem(R.string.menu_help, R.drawable.ic_help, arrowVisibility = false)

   object Logout :
      ProfileMenuItem(R.string.menu_logout, R.drawable.ic_logout, arrowVisibility = false)

   companion object {
      private const val balance = 1593
      fun getItems() = listOf(
         TradeStore, PaymentMethod, Balance(balance), History, RestorePurchase, Help, Logout
      )
   }
}
