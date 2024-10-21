package com.example.e_commerceadmin.NetworkApi.ApiService

import com.example.e_commerceadmin.constant.Constants
import com.example.e_commerceadmin.model.CoponsModel.AllDiscountCodes
import com.example.e_commerceadmin.model.CoponsModel.DiscountCodeRequest
import com.example.e_commerceadmin.model.CoponsModel.DiscountCodeResponse
import com.example.e_commerceadmin.model.CoponsModel.PriceRuleRequest
import com.example.e_commerceadmin.model.CoponsModel.PriceRuleResponse
import com.example.e_commerceadmin.model.CoponsModel.PriceRuleResponsePost
import com.example.e_commerceadmin.model.CustomersByEmailResponse
import com.example.e_commerceadmin.model.ProductModel.AllProductResponse
import com.example.e_commerceadmin.model.ProductModel.OneProductsResponse

import com.example.e_commerceadmin.model.ProductModel.ProdCountResponse
import com.example.e_commerceadmin.model.ProductModel.ProductBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

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




  @Headers("Content-Type:application/json", "X-Shopify-Access-Token:"+ Constants.Access_Token)
  @DELETE("products/{product_id}.json")
  suspend fun deleteProduct(
    @Path("product_id") productId: Long?
  ): Response<Unit>

/////////////////
  //rules
@Headers("Content-Type:application/json","X-Shopify-Access-Token:"+Constants.Access_Token)
@GET("price_rules.json?")
suspend fun getPriceRules(): PriceRuleResponse

  @Headers("Content-Type:application/json","X-Shopify-Access-Token:"+Constants.Access_Token)
  @POST("price_rules.json")
  suspend fun createPriceRule( @Body body : PriceRuleRequest ): PriceRuleResponsePost

  @Headers("Content-Type:application/json","X-Shopify-Access-Token:"+Constants.Access_Token)
  @DELETE("price_rules/{price_rule_id}.json")
  suspend fun deletePriceRule(
    @Path("price_rule_id") ruleID : Long
  ): Response<Unit>

  @Headers("Content-Type:application/json","X-Shopify-Access-Token:"+Constants.Access_Token)
  @PUT("price_rules/{price_rule_id}.json")
  suspend fun updatePriceRule(
    @Path("price_rule_id") ruleID : Long,
    @Body body: PriceRuleResponsePost
  ): PriceRuleResponsePost


  /////////
  //discount
  @Headers("Content-Type:application/json","X-Shopify-Access-Token:"+Constants.Access_Token)
  @GET("price_rules/{price_rule_id}/discount_codes.json")
  suspend fun getDiscounts(@Path("price_rule_id") ruleID : Long): AllDiscountCodes

  @Headers("Content-Type:application/json","X-Shopify-Access-Token:"+Constants.Access_Token)
  @POST("price_rules/{price_rule_id}/discount_codes.json")
  suspend fun createDiscountCode( @Path("price_rule_id") ruleID : Long, @Body body: DiscountCodeRequest): DiscountCodeResponse

  @Headers("Content-Type:application/json","X-Shopify-Access-Token:"+Constants.Access_Token)
  @DELETE("price_rules/{price_rule_id}/discount_codes/{discount_code_id}.json")
  suspend fun deleteDiscount(
    @Path("price_rule_id") ruleID : Long,
    @Path("discount_code_id") discountId : Long,
  ): Response<Unit>

  ////
  //auth

  @Headers("Content-Type:application/json", "X-Shopify-Access-Token:"+ Constants.Access_Token)
  @GET("customers/search.json")
  suspend fun getCustomerByEmail(
    @Query("query") emailQuery: String
  ): CustomersByEmailResponse


//
//  @Headers("Content-Type:application/json", "X-Shopify-Access-Token:"+ Constants.Access_Token)
//  @GET("customers.json")
//  suspend fun getUsers(): AllUsersResponse

}