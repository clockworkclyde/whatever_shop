package com.github.clockworkclyde.eshop.ui.categories

import com.github.clockworkclyde.core.common.FlowResultList
import com.github.clockworkclyde.core.dto.Result
import com.github.clockworkclyde.core.presentation.viewmodels.BaseFlowViewModel
import com.github.clockworkclyde.core.utils.get
import com.github.clockworkclyde.core.utils.loadingResult
import com.github.clockworkclyde.domain.GlobalConstants
import com.github.clockworkclyde.domain.usecases.search.SearchProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchProductViewModel @Inject constructor(
   private val searchProductWithQuery: SearchProductsUseCase
) : BaseFlowViewModel() {

   val searchFlow: MutableStateFlow<String> by lazy {
      MutableStateFlow("")
   }

   @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
   override val resultFlow: FlowResultList<String> by lazy {
      searchFlow
         .debounce(GlobalConstants.DEBOUNCE_SEARCH_VIEW)
         .flatMapLatest {
            flow {
               emit(loadingResult())
               emit(searchProductWithQuery(it))
            }
         }
   }

   val items by lazy {
      resultFlow
         .filter { it is Result.Success }
         .distinctUntilChanged()
         .mapNotNull { it.get() }
   }

   fun onSearchSuggestionClicked(index: Int, item: String) {
      Timber.e(" suggestion selected : $item at $index ")
   }
}