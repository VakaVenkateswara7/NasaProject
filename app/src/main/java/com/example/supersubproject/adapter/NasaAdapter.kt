package com.example.supersubproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.supersubproject.R
import com.example.supersubproject.model.NasaResponseItem

class NasaAdapter(private val nasaList: List<NasaResponseItem>) :
    RecyclerView.Adapter<NasaAdapter.MyViewHolder>() {


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = itemView.findViewById(R.id.textView)
        val desc: TextView = itemView.findViewById(R.id.textView2)
        val nasaIv: ImageView = itemView.findViewById(R.id.imageview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val nasaModel = nasaList[position]
        holder.title.text = nasaModel.title
        holder.desc.text = nasaModel.explanation
        Glide.with(holder.itemView.context).load(nasaModel.hdurl).into(holder.nasaIv)
    }

    override fun getItemCount(): Int {
        return nasaList.size
    }
}