package com.github.clockworkclyde.eshop.ui.details

import com.github.clockworkclyde.core.presentation.fragments.BaseFragment
import com.github.clockworkclyde.core.presentation.viewmodels.BaseFlowViewModel
import com.github.clockworkclyde.eshop.databinding.LayoutProductDetailsSheetBinding

class ProductBottomSheetLayout: BaseFragment<LayoutProductDetailsSheetBinding, BaseFlowViewModel>() {

   override fun inflateView() = LayoutProductDetailsSheetBinding.inflate(layoutInflater)

   companion object {
      fun newInstance() = ProductBottomSheetLayout()
   }

}