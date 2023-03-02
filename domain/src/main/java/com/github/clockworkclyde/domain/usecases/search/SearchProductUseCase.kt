package com.github.clockworkclyde.domain.usecases.search

import com.github.clockworkclyde.core.common.ResultList
import com.github.clockworkclyde.domain.repository.ISearchSuggestionRepository
import com.github.clockworkclyde.domain.usecases.IUseCase
import javax.inject.Inject

class SearchProductsUseCase @Inject constructor(
   private val repository: ISearchSuggestionRepository
): ISearchProductsUseCase {

   override suspend fun invoke(param: String): ResultList<String> {
      return repository.getSuggestions()
   }
}

interface ISearchProductsUseCase: IUseCase.SingleInOut<String, ResultList<String>>
