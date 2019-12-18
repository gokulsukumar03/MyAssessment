package com.sukumar.tetrasoft.base

import com.sukumar.tetrasoft.base.ApiBaseConfig.Companion.API_KEY
import com.sukumar.tetrasoft.base.ApiBaseConfig.Companion.POPULAR
import com.sukumar.tetrasoft.module.mostPopular.dto.PopularDto
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface{

    @GET(POPULAR)
    fun getPopularData(@Query(API_KEY) apiKey : String) : Single<Response<PopularDto>>
}