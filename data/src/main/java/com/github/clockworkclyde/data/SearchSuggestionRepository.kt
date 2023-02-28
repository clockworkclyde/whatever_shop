package com.github.clockworkclyde.data

import com.github.clockworkclyde.core.common.ResultList
import com.github.clockworkclyde.core.utils.mapToSuccessResultOrEmpty
import com.github.clockworkclyde.core.utils.runResultSuspendCatch
import com.github.clockworkclyde.data.sources.ISearchSuggestionRemoteDataSource
import com.github.clockworkclyde.domain.repository.ISearchSuggestionRepository
import javax.inject.Inject

class SearchSuggestionRepository @Inject constructor(
   private val remoteDataSource: ISearchSuggestionRemoteDataSource
) : ISearchSuggestionRepository {

   override suspend fun getSuggestions(): ResultList<String> {
      return runResultSuspendCatch { remoteDataSource.getSuggestions().mapToSuccessResultOrEmpty() }
   }
}