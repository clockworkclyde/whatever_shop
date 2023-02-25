package com.github.clockworkclyde.eshop.ui.catalog

import com.github.clockworkclyde.core.presentation.fragments.BaseFragment
import com.github.clockworkclyde.core.presentation.viewmodels.BaseFlowViewModel
import com.github.clockworkclyde.eshop.databinding.FragmentShopCatalogBinding

class ShopCatalogFragment: BaseFragment<FragmentShopCatalogBinding, BaseFlowViewModel>() {

   override fun inflateView() = FragmentShopCatalogBinding.inflate(layoutInflater)

}