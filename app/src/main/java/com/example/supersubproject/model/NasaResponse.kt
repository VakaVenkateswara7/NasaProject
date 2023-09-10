package com.example.supersubproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "nasa")
data class NasaResponseItem(
	@PrimaryKey(autoGenerate = true)
	var id: Long = 0,

	@SerializedName("date")
	var date: String? = null,

	@SerializedName("copyright")
	var copyright: String? = null,

	@SerializedName("media_type")
	var mediaType: String? = null,

	@SerializedName("hdurl")
	var hdurl: String? = null,

	@SerializedName("service_version")
	var serviceVersion: String? = null,

	@SerializedName("explanation")
	var explanation: String? = null,

	@SerializedName("title")
	var title: String? = null,

	@SerializedName("url")
	var url: String? = null
)
