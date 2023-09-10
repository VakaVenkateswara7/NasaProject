package com.example.supersubproject.repository.api.main

import androidx.lifecycle.LiveData
import com.example.supersubproject.model.NasaResponseItem
import retrofit2.http.GET
import retrofit2.http.Query

interface MainApiService {

 /*   @GET("apod")
    fun getNasaData(@Query("api_key") apiKey:String, @Query("count") count:Int) : LiveData<ApiResponse<List<NasaResponseItem>>>*/

    @GET("apod")
    fun getNasaData(@Query("api_key") apiKey:String,
                 @Query("start_date") startDate:String,
                 @Query("end_date") endDate:String) : LiveData<ApiResponse<List<NasaResponseItem>>>

}