package com.example.e_commerceadmin.NetworkApi.ApiService

import com.example.e_commerceadmin.constant.Constants
import com.example.e_commerceadmin.model.ProductModel.AllProductResponse
import com.example.e_commerceadmin.model.ProductModel.OneProductsResponse

import com.example.e_commerceadmin.model.ProductModel.ProdCountResponse
import com.example.e_commerceadmin.model.ProductModel.ProductBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductsApiServices {
  @Headers("Content-Type:application/json", "X-Shopify-Access-Token:"+ Constants.Access_Token)
    @GET("products/count.json")
    suspend fun getCountOfProducts(): ProdCountResponse

    @Headers("Content-Type:application/json", "X-Shopify-Access-Token:"+ Constants.Access_Token)
    @GET("products.json")
    suspend fun getProducts(): AllProductResponse

    //@Body : by5aly el parameter included as the body of the HTTP POST request
  @Headers("Content-Type:application/json", "X-Shopify-Access-Token:"+ Constants.Access_Token)
  @POST("products.json")
  suspend fun createProduct(@Body body: ProductBody): OneProductsResponse
//
  @Headers("Content-Type:application/json", "X-Shopify-Access-Token:"+ Constants.Access_Token)
  @PUT("products/{product_id}.json")
  suspend fun updateProduct(
    @Path("product_id") productId : Long,
    @Body body: OneProductsResponse
  ): OneProductsResponse


//  @Headers("Content-Type:application/json", "X-Shopify-Access-Token:"+ Constants.Access_Token)
//  @PUT("products/{product_id}/images.json")
//  suspend fun uploadImageToProduct(
//    @Path("product_id") productId : Long,
//    @Body body: SingleImageBody
//  ): SingleImageResponse


  @Headers("Content-Type:application/json", "X-Shopify-Access-Token:"+ Constants.Access_Token)
  @DELETE("products/{product_id}.json")
  suspend fun deleteProduct(
    @Path("product_id") productId: Long?
  )



}