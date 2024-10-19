package com.example.e_commerceadmin.NetworkApi

import com.example.e_commerceadmin.NetworkApi.ApiService.ProductsApiServices
import com.example.e_commerceadmin.constant.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object NetworkRetrofit {

    val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

fun productsApiService():ProductsApiServices{
    return retrofit.create (ProductsApiServices::class.java)
}
 // val productsApiService: ProductsApiServices = retrofit.create (ProductsApiServices::class.java)




}