package com.github.clockworkclyde.eshop.ui.details

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.github.clockworkclyde.core.common.FlowResult
import com.github.clockworkclyde.core.dto.Result
import com.github.clockworkclyde.core.dto.UEvent
import com.github.clockworkclyde.core.presentation.viewmodels.BaseFlowViewModel
import com.github.clockworkclyde.core.utils.*
import com.github.clockworkclyde.domain.model.product.ProductDetails
import com.github.clockworkclyde.domain.usecases.details.AddProductToFavoritesUseCase
import com.github.clockworkclyde.domain.usecases.details.CheckProductContainsFavoritesUseCase
import com.github.clockworkclyde.domain.usecases.details.GetProductDetailsUseCase
import com.github.clockworkclyde.domain.usecases.details.RemoveFavoriteProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
   private val getProductDetails: GetProductDetailsUseCase,
   private val addToFavorites: AddProductToFavoritesUseCase,
   private val checkProductContainsFavorites: CheckProductContainsFavoritesUseCase,
   private val removeFromFavorites: RemoveFavoriteProductUseCase,
) : BaseFlowViewModel(), DefaultLifecycleObserver {

   val item: StateFlow<ProductDetails?> by lazy {
      resultFlow
         .filter { it is Result.Success }
         .distinctUntilChanged()
         .mapNotNull { it.get() }
         .stateIn(viewModelScope, SharingStarted.Lazily, null)
   }

   override val resultFlow: FlowResult<ProductDetails> by lazy {
      onEventFlow<UEvent.Fetch, Result<ProductDetails>> {
         emit(loadingResult())
         emit(getProductDetails())
      }
   }

   override fun onCreate(owner: LifecycleOwner) {
      super.onCreate(owner)
      processEvent(UEvent.Fetch)
   }

   fun onAddToCartClicked() {
      val quantity = quantity.value
      val item = item.value
      val selectedColor = item?.colors?.elementAtOrNull(selectedColor.value)
   }

   fun onRetryClicked() {
      processEvent(UEvent.Fetch)
   }

   val quantity = MutableStateFlow(INITIAL_QUANTITY)
   val selectedColor = MutableStateFlow(INITIAL_COLOR_INDEX)

   val totalPrice: StateFlow<Int> by lazy {
      item.combine(quantity) { item, quantity ->
         item?.price.orDefault() * quantity
      }.stateIn(viewModelScope, SharingStarted.Eagerly, INITIAL_TOTAL_PRICE)
   }

   fun onMoreButtonClicked() {
      quantity.value.let {
         if (it < 25) {
            quantity.value = it.inc()
         }
      }
   }

   fun onLessButtonClicked() {
      quantity.value.let {
         if (it > 1)
            quantity.value = it.dec()
      }
   }

   fun onColorClicked(index: Int, color: String) {
      selectedColor.value = index
   }

   @OptIn(ExperimentalCoroutinesApi::class)
   val isProductContainsFavorites: StateFlow<Boolean> by lazy {
      item.flatMapLatest { item ->
         checkProductContainsFavorites(item?.name)
      }.stateIn(viewModelScope, SharingStarted.Lazily, false)
   }

   fun onFavoriteClicked() {
      item.value?.let {
         viewModelScope.launch {
            if (!isProductContainsFavorites.value) addToFavorites.invoke(it.name)
            else removeFromFavorites.invoke(it.name)
         }
      }
   }

   companion object {
      const val INITIAL_TOTAL_PRICE = 0
      const val INITIAL_QUANTITY = 1
      const val INITIAL_COLOR_INDEX = 0
   }
}