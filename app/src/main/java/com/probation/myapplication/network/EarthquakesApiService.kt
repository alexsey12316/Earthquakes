package com.probation.myapplication.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.probation.myapplication.network.earthquake.FeatureCollection
import com.probation.myapplication.network.statistics.Statistics
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


//private const val BASE_URL="https://api.geonet.org.nz/"

//private val moshi:Moshi= Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
//private val retrofit:Retrofit=Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
//    .addCallAdapterFactory(CoroutineCallAdapterFactory()).baseUrl(BASE_URL).build()


interface EarthquakesApiService {
    @GET(value = "quake")
    fun getEarthquakesAsync(@Query(value = "MMI")mmi:String="0" ):Deferred<FeatureCollection>

    @GET(value = "quake/{publicID}")
    fun getByPublicIDAsync(@Path("publicID")id:String ):Deferred<FeatureCollection>

    @GET(value = "quake/stats")
    fun getStatisticsAsync():Deferred<Statistics>

}

//object EarthquakesApi
//{
//    val retrofitServise: EarthquakesApiService by lazy { retrofit.create(
//        EarthquakesApiService::class.java) }
//}