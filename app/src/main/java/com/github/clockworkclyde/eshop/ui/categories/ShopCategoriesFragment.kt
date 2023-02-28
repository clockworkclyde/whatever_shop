package com.github.clockworkclyde.eshop.ui.categories

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import com.github.clockworkclyde.core.dto.Result
import com.github.clockworkclyde.core.presentation.fragments.BaseFragment
import com.github.clockworkclyde.core.utils.*
import com.github.clockworkclyde.domain.model.product.BaseProductCard
import com.github.clockworkclyde.domain.model.product.CommonCategory
import com.github.clockworkclyde.domain.model.product.ProductCardHorizontalItem
import com.github.clockworkclyde.eshop.R
import com.github.clockworkclyde.eshop.databinding.FragmentShopCategoriesBinding
import com.github.clockworkclyde.eshop.ui.categories.adapters.ShopCategoriesCommonAdapter
import com.github.clockworkclyde.eshop.ui.categories.adapters.ShopCategoriesRootAdapter
import com.github.clockworkclyde.eshop.ui.categories.model.ProductCardDiscountProgress
import com.github.clockworkclyde.eshop.ui.categories.model.ProductCardProgress
import com.github.clockworkclyde.eshop.ui.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopCategoriesFragment :
   BaseFragment<FragmentShopCategoriesBinding, ShopCategoriesViewModel>() {

   override fun inflateView() = FragmentShopCategoriesBinding.inflate(layoutInflater)

   override val viewModel: ShopCategoriesViewModel by viewModels()
   private val searchViewModel: SearchProductViewModel by viewModels()
   private val profileViewModel: ProfileViewModel by navGraphViewModels(R.id.host_nav_graph) { defaultViewModelProviderFactory }

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

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      lifecycle.addObserver(profileViewModel)
   }

   override fun onPause() {
      super.onPause()
      view?.hideKeyboard()
      view?.clearFocus()
   }

   override fun initBinding(binding: FragmentShopCategoriesBinding) {
      binding.viewModel = viewModel
      binding.searchVM = searchViewModel
      binding.lifecycleOwner = viewLifecycleOwner
   }

   override fun initViews() {
      setUpRecyclerViews()
      setUpCommonCategories()
      observeHorizontalItems()
      observeSearchSuggestions()
      setUpToolbar()
      setUpProfilePicture()
   }

   private fun observeSearchSuggestions() {
      with(binding.searchTextInput) {
         this.applyUserInteractionTyped<String> { index, item ->
            searchViewModel.onSearchSuggestionClicked(index, item)
         }
         searchViewModel.items.collectWhileStarted {
            this.setListAdapter(it, R.layout.item_dropdown_list)
         }
      }
   }

   private fun setUpProfilePicture() {
      profileViewModel.resultFlow.collectWhileStarted {
         it.run {
            when (this) {
               is Result.Success -> loadUserPhotoInto(
                  binding.toolbarView.imgBtnProfilePic,
                  this.data.pic?.bitmap
               )
               else -> setEmptyPhotoTemplate(binding.toolbarView.imgBtnProfilePic)
            }
         }
      }
   }

   private val emptyPhotoTemplate: Drawable? by lazy {
      ContextCompat.getDrawable(requireContext(), R.drawable.ic_user_photo)
   }

   private fun setEmptyPhotoTemplate(view: ImageView) =
      view.setImageDrawable(emptyPhotoTemplate)

   private fun setUpRecyclerViews() {
      binding.rootRecyclerView.adapter = adapter
      binding.topCategoriesRecyclerView.adapter = commonCategoryAdapter
   }

   private fun loadUserPhotoInto(view: ImageView, bitmap: Bitmap?) {
      if (bitmap == null) {
         setEmptyPhotoTemplate(view)
         return
      }
      Glide.with(binding.root)
         .loadCircleRoundedBitmap(bitmap, view)
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