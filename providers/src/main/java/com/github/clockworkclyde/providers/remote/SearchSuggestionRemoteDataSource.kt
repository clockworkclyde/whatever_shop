package com.github.clockworkclyde.providers.remote

import com.github.clockworkclyde.data.sources.ISearchSuggestionRemoteDataSource
import com.github.clockworkclyde.providers.remote.api.EShopApi
import javax.inject.Inject

class SearchSuggestionRemoteDataSource @Inject constructor(
   private val api: EShopApi
): ISearchSuggestionRemoteDataSource {

   override suspend fun getSuggestions(): List<String> {
      return api.getSearchSuggestions().items
   }

}