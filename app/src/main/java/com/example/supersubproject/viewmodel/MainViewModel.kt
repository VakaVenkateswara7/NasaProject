package com.example.supersubproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.supersubproject.model.NasaResponseItem
import com.example.supersubproject.repository.db.NasaRepository
import com.example.supersubproject.util.Resource

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var nasaRepository: NasaRepository = NasaRepository(application)

    fun reteriveNasaInfo(): LiveData<Resource<List<NasaResponseItem>>> = nasaRepository.loadNasaData()

    fun deleteDb() = nasaRepository.deleteDb()

}