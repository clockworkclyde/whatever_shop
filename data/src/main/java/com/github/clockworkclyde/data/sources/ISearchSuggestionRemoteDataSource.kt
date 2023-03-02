package com.github.clockworkclyde.data.sources

interface ISearchSuggestionRemoteDataSource {
   suspend fun getSuggestions(): List<String>
}