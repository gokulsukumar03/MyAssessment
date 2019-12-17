package com.sukumar.tetrasoft.module.mostPopular.dto

import com.google.gson.annotations.SerializedName

data class ResultsItem(


    @field:SerializedName("byline")
    val byline: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("published_date")
    val publishedDate: String? = null
)