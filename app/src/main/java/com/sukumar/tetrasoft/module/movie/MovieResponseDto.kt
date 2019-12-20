package com.sukumar.movieapiapplication.ui
import com.google.gson.annotations.SerializedName
data class MovieResponseDto(

	@field:SerializedName("Search")
	val search: ArrayList<SearchItem?>? = null,

	@field:SerializedName("totalResults")
	val totalResults: String? = null,

	@field:SerializedName("Response")
	val response: String? = null
)