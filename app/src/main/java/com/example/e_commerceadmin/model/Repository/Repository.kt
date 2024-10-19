package com.example.e_commerceadmin.model.Repository

import com.example.e_commerceadmin.NetworkApi.NetworkRetrofit
import com.example.e_commerceadmin.model.ProductModel.AllProductResponse
import com.example.e_commerceadmin.model.ProductModel.OneProductsResponse

import com.example.e_commerceadmin.model.ProductModel.ProdCountResponse
import com.example.e_commerceadmin.model.ProductModel.ProductBody
import com.example.e_commerceadmin.model.RemoteData.productRemote.IRemoteProductDataSource
import com.example.e_commerceadmin.model.RemoteData.productRemote.RemoteProductDataSource
import kotlinx.coroutines.flow.Flow

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

     suspend fun createProduct(body: ProductBody): OneProductsResponse {
        return remoteproductSource.postProduct(body)
    }

     suspend fun deleteProduct(productId: Long?) {
         remoteproductSource.deleteProduct(productId)
    }

    suspend fun updateProduct(
        productId: Long,
        product: OneProductsResponse
    ): OneProductsResponse {
        return remoteproductSource.updateProduct(productId,product)
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

