package com.example.e_commerceadmin.model.Repository

import com.example.e_commerceadmin.NetworkApi.NetworkRetrofit

import com.example.e_commerceadmin.model.CoponsModel.DiscountCode
import com.example.e_commerceadmin.model.CoponsModel.DiscountCodeRequest
import com.example.e_commerceadmin.model.CoponsModel.DiscountCodeResponse
import com.example.e_commerceadmin.model.CoponsModel.PriceRule
import com.example.e_commerceadmin.model.CoponsModel.PriceRuleRequest
import com.example.e_commerceadmin.model.CoponsModel.PriceRuleResponsePost
import com.example.e_commerceadmin.model.CustomersByEmailResponse
import com.example.e_commerceadmin.model.ProductModel.AllProductResponse
import com.example.e_commerceadmin.model.ProductModel.OneProductsResponse

import com.example.e_commerceadmin.model.ProductModel.ProdCountResponse
import com.example.e_commerceadmin.model.ProductModel.ProductBody
import com.example.e_commerceadmin.model.RemoteData.productRemote.IRemoteProductDataSource
import com.example.e_commerceadmin.model.RemoteData.productRemote.RemoteProductDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Response

class Repository( private val remoteproductSource: IRemoteProductDataSource,) {


    companion object {
        // Singleton instance
        @Volatile
        private var INSTANCE: Repository? = null
        fun getInstance( productRemoteSource: IRemoteProductDataSource): Repository {

            return INSTANCE ?: synchronized(this) {
                val instance = Repository(productRemoteSource)

                INSTANCE = instance
                instance
            }
        }
    }


      fun getCountOfProducts(): Flow<ProdCountResponse> {
        return remoteproductSource.getProductsCount()
    }
     fun getAllProducts():Flow<AllProductResponse>{
        return remoteproductSource.getAllProducts()
    }

        fun getCustomerByEmail(email:String): Flow<CustomersByEmailResponse> {
        return remoteproductSource.getCustomerByEmail(email)

    }

//     fun getUsers(): Flow<AllUsersResponse> {
//         return remoteproductSource.getUsers()
//    }

     suspend fun createProduct(body: ProductBody): OneProductsResponse {
        return remoteproductSource.postProduct(body)
    }

     suspend fun deleteProduct(productId: Long?):Response<Unit> {
      return   remoteproductSource.deleteProduct(productId)
    }

    suspend fun updateProduct(
        productId: Long,
        product: OneProductsResponse
    ): OneProductsResponse {
        return remoteproductSource.updateProduct(productId,product)
    }
////////////////
    suspend fun getPriceRules(): Flow<List<PriceRule>> {
        return flowOf(remoteproductSource.getPriceRules())
    }

    suspend fun createPriceRules( rulerequest: PriceRuleRequest):PriceRuleResponsePost{
        return remoteproductSource.createPriceRules(rulerequest)
    }

    suspend fun updatePriceRules(ruleID: Long, rulerequest: PriceRuleResponsePost):Flow<PriceRuleResponsePost>{
        return flowOf(remoteproductSource.updatePriceRule(ruleID,rulerequest))

    }
    suspend fun deleteRule(ruleID : Long):Response<Unit>{
        return remoteproductSource.drleteRule(ruleID)
    }
//

    //////
    suspend fun getDiscounts(ruleID: Long):Flow<List<DiscountCode>>{
        return flowOf(remoteproductSource.getDiscounts(ruleID))
    }

    suspend fun createDiscount(ruleID: Long,rulerequest: DiscountCodeRequest):DiscountCodeResponse{
        return  remoteproductSource.createDiscount(ruleID,rulerequest)
    }


     suspend fun deleteDiscount(ruleID: Long, discountId: Long):Response<Unit>
     {

         return remoteproductSource.deleteDiscount(ruleID, discountId)

     }



}









//
//    private const val API_KEY = "23fcb7358724ab32ace0a9e4b2398cf1"
//
//    interface ApiWeatherService {
//        @GET("weather")
//        suspend fun getCurrentWeather(
//            @Query("lat") latitude: Double,
//            @Query("lon") longitude: Double,
//            @Query("units") units: String = "metric",
//            @Query("lang") lang: String
//        ): CurrentWeatherResponse
//
//        @GET("forecast")
//        suspend fun getForecastWeather(
//            @Query("lat") latitude: Double,
//            @Query("lon") longitude: Double,
//            @Query("units") units: String = "metric",
//            @Query("lang") lang: String
//        ): ForecastResponse
//
//        companion object {
//            private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
//
//            operator fun invoke(): ApiWeatherService {
//                val requestInterceptor = Interceptor { chain ->
//                    val url = chain.request().url().newBuilder()
//                        .addQueryParameter("appid", API_KEY)
//                        .build()
//                    val request = chain.request().newBuilder().url(url).build()
//                    return@Interceptor chain.proceed(request)
//                }
//
//                val okHttpClient = OkHttpClient.Builder()
//                    .addInterceptor(requestInterceptor)
//                    .build()
//
//                return Retrofit.Builder()
//                    .client(okHttpClient)
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//                    .create(ApiWeatherService::class.java)
//            }
//        }
//    }

