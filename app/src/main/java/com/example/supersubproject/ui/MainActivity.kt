package com.example.supersubproject.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.supersubproject.R
import com.example.supersubproject.model.NasaResponseItem
import com.example.supersubproject.util.Status
import com.example.supersubproject.viewmodel.MainViewModel
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var mainViewModel: MainViewModel? = null
    var recyclerView: RecyclerView? = null
    lateinit var imageView:ImageView
    lateinit var textView: TextView
    lateinit var textView2:TextView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    var list = mutableListOf<NasaResponseItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        recyclerView = findViewById(R.id.recyclerView)
        textView = findViewById(R.id.textView)
        textView2 = findViewById(R.id.textView2)
        imageView = findViewById(R.id.imageview)
        swipeRefreshLayout = findViewById(R.id.refreshLayout)
        recyclerView?.layoutManager = LinearLayoutManager(this)

        loadData()
        swipeRefreshLayout.setOnRefreshListener {
            if (list.size > 0) {
                swipeRefreshLayout.isRefreshing = false
                refreshLogic()
            }
        }
    }

    private fun refreshLogic() {
        val nasaItem = list[Random.nextInt(0,list.size-1)]
        textView.text = nasaItem.title
        textView2.text = nasaItem.explanation
        Glide.with(this).load(nasaItem.url).into(imageView)
    }

    private fun loadData() {
        mainViewModel?.reteriveNasaInfo()?.observe(this) {
            when (it.status) {

                Status.SUCCESS -> {
                    if (it != null) {
                        swipeRefreshLayout.isRefreshing = false
                        /*    val adapter = NasaAdapter(it.data as List<NasaResponseItem>)
                        recyclerView?.adapter = adapter*/
                        list.addAll(it.data as List<NasaResponseItem>)
                        refreshLogic()
                    }
                }

                Status.ERROR -> {
                    swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }

                Status.LOADING -> {
                    swipeRefreshLayout.isRefreshing = true
                    //Toast.makeText(this, "Data Loading", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}