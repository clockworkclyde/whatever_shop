package com.github.clockworkclyde.eshop.ui.details

import androidx.fragment.app.viewModels
import com.github.clockworkclyde.core.presentation.fragments.BaseFragment
import com.github.clockworkclyde.eshop.R
import com.github.clockworkclyde.eshop.databinding.LayoutProductDetailsSheetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductBottomSheetLayout: BaseFragment<LayoutProductDetailsSheetBinding, ProductDetailsViewModel>() {

   override val viewModel: ProductDetailsViewModel by viewModels ({ requireParentFragment() })

   override fun inflateView() = LayoutProductDetailsSheetBinding.inflate(layoutInflater)

   override fun initBinding(binding: LayoutProductDetailsSheetBinding) {
      binding.viewModel = viewModel
   }

   override fun initViews() {
      observeQuantity()
      observeTotalPrice()
   }

   private fun observeQuantity() {
      viewModel.quantity.collectWhileStarted {
         binding.quantityTV.text = it.toString()
      }
   }

   private fun observeTotalPrice() {
      val totalPriceFormat = getString(R.string.format_price_us_with_cents)
      val priceStub = getString(R.string.price_stub)
      viewModel.totalPrice.collectWhileStarted { total ->
         binding.totalPriceTV.text = total.takeIf { it > 1 }?.let {
            totalPriceFormat.format(it)
         } ?: priceStub
      }
   }

   companion object {
      fun newInstance() = ProductBottomSheetLayout()
   }
}