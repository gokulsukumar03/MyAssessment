package com.sukumar.tetrasoft.base

import okhttp3.OkHttpClient

object OkHttpProvider {

    val instance: OkHttpClient = OkHttpClient.Builder().build()
}