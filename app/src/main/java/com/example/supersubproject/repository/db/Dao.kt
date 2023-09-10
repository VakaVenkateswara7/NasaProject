package com.example.supersubproject.repository.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.supersubproject.model.NasaResponseItem

//Adding annotation to our Dao class
@Dao
interface Dao {

    @Insert
    fun insert(model: List<NasaResponseItem>)

    @Query("SELECT * FROM nasa")
    fun getAllInfo(): LiveData<List<NasaResponseItem>>

    @Query("DELETE FROM nasa")
    fun deleteAll()

}

