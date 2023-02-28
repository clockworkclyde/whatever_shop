package com.github.clockworkclyde.data.models.dto

import com.google.gson.annotations.SerializedName

data class SearchSuggestionsResponse(
   @SerializedName("words") val items: List<String>
)