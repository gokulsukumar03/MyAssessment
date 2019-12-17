package com.sukumar.tetrasoft.module.mostPopular.dto
import com.google.gson.annotations.SerializedName
data class PopularDto(

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("num_results")
	val numResults: Int? = null,

	@field:SerializedName("results")
	val results: ArrayList<ResultsItem?>? = null
)