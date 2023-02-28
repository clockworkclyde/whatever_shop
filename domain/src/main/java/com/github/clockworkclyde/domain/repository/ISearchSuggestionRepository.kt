package com.github.clockworkclyde.domain.repository

import com.github.clockworkclyde.core.common.ResultList

interface ISearchSuggestionRepository {
   suspend fun getSuggestions(): ResultList<String>
}