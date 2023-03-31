package com.example.moweapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moweapp.api.ApiUtils
import com.example.moweapp.databinding.NameItemBinding
import com.example.moweapp.model.Genre


class NameAdapter(val listener: OnItemClickListener,val genreList: List<Genre>) : RecyclerView.Adapter<NameAdapter.nameHolder>() {
    inner class nameHolder(val binding: NameItemBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): nameHolder {
        val design = NameItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return nameHolder(design)
    }

    override fun getItemCount(): Int {
        return genreList.size
    }

    override fun onBindViewHolder(holder: nameHolder, position: Int) {
        val genre = genreList[position]
        holder.binding.tvGenre.text = genre.name
        holder.binding.tvGenre.setOnClickListener {
            listener.onItemClick(genre.id.toString())
        }
    }

    interface OnItemClickListener {
        fun onItemClick(genreId: String)
    }


}