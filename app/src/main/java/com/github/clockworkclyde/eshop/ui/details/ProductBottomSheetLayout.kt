package com.github.clockworkclyde.eshop.ui.details

import androidx.fragment.app.viewModels
import com.github.clockworkclyde.core.presentation.fragments.BaseFragment
import com.github.clockworkclyde.eshop.databinding.LayoutProductDetailsSheetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductBottomSheetLayout: BaseFragment<LayoutProductDetailsSheetBinding, ProductDetailsViewModel>() {

   override val viewModel: ProductDetailsViewModel by viewModels ({ requireParentFragment() })

   override fun inflateView() = LayoutProductDetailsSheetBinding.inflate(layoutInflater)

   override fun initBinding(binding: LayoutProductDetailsSheetBinding) {
      binding.viewModel = viewModel
   }

   companion object {
      fun newInstance() = ProductBottomSheetLayout()
   }
}