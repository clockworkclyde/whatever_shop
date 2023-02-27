package com.github.clockworkclyde.eshop.ui.categories

import androidx.fragment.app.viewModels
import com.github.clockworkclyde.core.presentation.fragments.BaseFragment
import com.github.clockworkclyde.core.utils.*
import com.github.clockworkclyde.eshop.databinding.FragmentShopCategoriesBinding
import com.github.clockworkclyde.eshop.ui.categories.adapters.ShopCategoriesRootAdapter
import com.github.clockworkclyde.domain.model.product.BaseProductCard
import com.github.clockworkclyde.domain.model.product.CommonCategory
import com.github.clockworkclyde.domain.model.product.ProductCardHorizontalItem
import com.github.clockworkclyde.eshop.ui.categories.adapters.ShopCategoriesCommonAdapter
import com.github.clockworkclyde.eshop.ui.categories.model.ProductCardDiscountProgress
import com.github.clockworkclyde.eshop.ui.categories.model.ProductCardProgress
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopCategoriesFragment :
   BaseFragment<FragmentShopCategoriesBinding, ShopCategoriesViewModel>() {

   override fun inflateView() = FragmentShopCategoriesBinding.inflate(layoutInflater)

   override val viewModel: ShopCategoriesViewModel by viewModels()

   private val adapter by lazy {
      ShopCategoriesRootAdapter(
         ::onHorizontalItemExpandClick,
         ::onProductCardClick,
         ::onProductCardAddToCartClick
      )
   }

   private val commonCategoryAdapter by lazy {
      ShopCategoriesCommonAdapter(
         ::onCommonCategoryClick
      )
   }

   override fun initViews() {
      setUpRecyclerViews()
      setUpCommonCategories()
      observeHorizontalItems()
      setUpToolbar()
   }

   private fun setUpRecyclerViews() {
      binding.rootRecyclerView.adapter = adapter
      binding.topCategoriesRecyclerView.adapter = commonCategoryAdapter
   }

   private fun setUpCommonCategories() {
      viewModel.supportFlow.collectWhileStarted { commonCategories ->
         commonCategories
            .applyIfError { commonCategoryAdapter.clear(); toast(it) }
            .applyIfSuccess { commonCategoryAdapter.items = it }
      }
   }

   private fun setUpToolbar() {
      binding.toolbarView.apply {
         imgBtnNavDrawer.safeClick { viewModel.onProfilePictureClicked() }
         imgBtnNavDrawer.safeClick { viewModel.onNavDrawerClicked() }
      }
   }

   private fun observeHorizontalItems() {
      viewModel.resultFlow.collectWhileStarted { horizontalItemsResult ->
         horizontalItemsResult
            .applyIfLoading { setLoadingState() }
            .applyIfSuccess { adapter.items = it }
            .applyIfError { adapter.clear(); toast(it) }
      }
   }

   private fun setLoadingState() {
      adapter.clear()
      adapter.items = IntRange(0, 3).map { index ->
         ProductCardHorizontalItem(
            title = "",
            items = IntRange(0, 5).map {
               if (index % 2 == 0) ProductCardProgress else ProductCardDiscountProgress
            }
         )
      }
   }

   private fun onCommonCategoryClick(item: CommonCategory) {
      viewModel.onCommonCategoryClicked(item)
   }

   private fun onHorizontalItemExpandClick(item: ProductCardHorizontalItem) {
      viewModel.onHorizontalItemExpandClicked(item)
   }

   private fun onProductCardClick(index: Int, item: BaseProductCard) {
      viewModel.onProductCardClicked(index, item)
   }

   private fun onProductCardAddToCartClick(index: Int, item: BaseProductCard) {
      viewModel.onProductCardAddToCartClicked(index, item)
   }
}