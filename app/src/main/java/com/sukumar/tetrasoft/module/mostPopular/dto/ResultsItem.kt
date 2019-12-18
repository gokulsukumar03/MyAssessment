package com.sukumar.tetrasoft.module.mostPopular.dto

import com.google.gson.annotations.SerializedName

data class ResultsItem(
    @field:SerializedName("byline")
    var byline: String? = null,

    @field:SerializedName("title")
    var title: String? = null,

    @field:SerializedName("published_date")
    var publishedDate: String? = null
)