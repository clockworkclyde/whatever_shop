package com.github.clockworkclyde.eshop.ui.categories

import androidx.fragment.app.viewModels
import com.github.clockworkclyde.core.presentation.fragments.BaseFragment
import com.github.clockworkclyde.core.presentation.viewmodels.BaseFlowViewModel
import com.github.clockworkclyde.eshop.databinding.FragmentShopCategoriesBinding
import com.github.clockworkclyde.eshop.ui.categories.adapters.ShopCategoriesRootAdapter
import com.github.clockworkclyde.eshop.ui.categories.model.BaseProductCard
import com.github.clockworkclyde.eshop.ui.categories.model.CommonCategory
import com.github.clockworkclyde.eshop.ui.categories.model.ProductCardHorizontalItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopCategoriesFragment :
   BaseFragment<FragmentShopCategoriesBinding, ShopCategoriesViewModel>() {

   override fun inflateView() = FragmentShopCategoriesBinding.inflate(layoutInflater)

   override val viewModel: ShopCategoriesViewModel by viewModels()

   private val adapter by lazy {
      ShopCategoriesRootAdapter(
         ::onCommonCategoryClick,
         ::onHorizontalItemExpandClick,
         ::onProductCardClick,
         ::onProductCardAddToCart
      )
   }

   override fun initViews() {

   }

   private fun onCommonCategoryClick(item: CommonCategory) {

   }

   private fun onHorizontalItemExpandClick(item: ProductCardHorizontalItem) {

   }

   private fun onProductCardClick(index: Int, item: BaseProductCard) {

   }

   private fun onProductCardAddToCart(index: Int, item: BaseProductCard) {

   }
}