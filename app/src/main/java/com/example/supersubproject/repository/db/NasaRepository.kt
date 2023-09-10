package com.example.supersubproject.repository.db

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import com.example.supersubproject.AppExecutors
import com.example.supersubproject.model.NasaResponseItem
import com.example.supersubproject.repository.api.main.ApiResponse
import com.example.supersubproject.repository.api.main.MainApiService
import com.example.supersubproject.repository.api.main.NetworkBoundResource
import com.example.supersubproject.util.LiveDataCallAdapterFactory
import com.example.supersubproject.util.Resource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.concurrent.Executors


class NasaRepository(application: Application) {

    var dao: Dao? = null
    var apiService: MainApiService? = null
    var appExecutors: AppExecutors? = null

    init {
        val database = NasaDatabase.getInstance(application)
        dao = database?.Dao()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        apiService = Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/planetary/")
            .client(client)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MainApiService::class.java)

        appExecutors = AppExecutors(
            Executors.newSingleThreadExecutor(),
            Executors.newFixedThreadPool(3),
            AppExecutors.MainThreadExecutor()
        )
    }


    fun loadNasaData(): LiveData<Resource<List<NasaResponseItem>>> {

        return object :
            NetworkBoundResource<List<NasaResponseItem>, List<NasaResponseItem>>(appExecutors) {
            override fun saveCallResult(item: List<NasaResponseItem>) {
                dao?.insert(item)
            }

            override fun shouldFetch(data: List<NasaResponseItem>?): Boolean {
                return data.isNullOrEmpty()
            }

            override fun loadFromDb(): LiveData<List<NasaResponseItem>> = dao?.getAllInfo()!!

            @SuppressLint("SimpleDateFormat")
            override fun createCall(): LiveData<ApiResponse<List<NasaResponseItem>>> {
                val time = Calendar.getInstance().time
                val formatter = SimpleDateFormat("yyyy-MM-dd")
                val current = formatter.format(time)
                return apiService?.getNasaData(
                    "FVpPSOcL9u0tUyxkPHFidgmwjdmkcrpZqePpcdTR",
                    "2023-08-02",
                    "2023-09-09"
                )!!
            }

        }.asLiveData()

    }

    fun deleteDb(){
       // dao?.deleteAll()
    }


}