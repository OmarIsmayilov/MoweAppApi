package com.example.moweapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.moweapp.databinding.MovieItemBinding
import com.example.moweapp.model.Movie
import com.example.moweapp.ui.fragment.HomeFragmentDirections
import com.example.moweapp.utils.Constants.IMAGE_BASE
import com.squareup.picasso.Picasso

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.viewHolder>()  {
    private val moviePopularList = arrayListOf<Movie>()

    inner class viewHolder(val binding : MovieItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val design = MovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return viewHolder(design)
    }

    override fun getItemCount(): Int {
        return moviePopularList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val movie = moviePopularList[position]
        val id = movie.id

        setData(movie,holder)

        holder.binding.cvMain.setOnClickListener{
            Navigation.findNavController(it).navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(id))
        }
    }

    private fun setData(movie: Movie,holder:viewHolder){
        Picasso.get().load(IMAGE_BASE+movie.posterPath).into(holder.binding.ivMovie)
        holder.binding.tvTitle.text = movie.title
        holder.binding.tvOverview.text = movie.overview
    }


    fun updateList(newList : ArrayList<Movie>){
        moviePopularList.clear()
        moviePopularList.addAll(newList)
        notifyDataSetChanged()
    }
}