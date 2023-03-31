package com.example.moweapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.moweapp.databinding.SingleMovieItemBinding
import com.example.moweapp.model.Movie
import com.example.moweapp.ui.fragment.GenreFragmentDirections
import com.example.moweapp.ui.fragment.HomeFragmentDirections
import com.example.moweapp.utils.Constants
import com.squareup.picasso.Picasso

class CategoryMovieAdapter :  RecyclerView.Adapter<CategoryMovieAdapter.viewHolder>()  {
    private var movieList = arrayListOf<Movie>()

    inner class viewHolder(val binding : SingleMovieItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val design = SingleMovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return viewHolder(design)
    }

    fun setFilteredList(newMovieList: ArrayList<Movie>){
        this.movieList = newMovieList
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val movie = movieList[position]

        setData(movie,holder)

        holder.binding.cvMain.setOnClickListener{
            Navigation.findNavController(it).navigate(GenreFragmentDirections.actionGenreFragmentToDetailFragment(id=movie.id))
        }
    }

    private fun setData(movie: Movie, holder:viewHolder){
        Picasso.get().load(Constants.IMAGE_BASE +movie.posterPath).into(holder.binding.ivMovie)
    }

    fun updateList(newList : ArrayList<Movie>){
        movieList.clear()
        movieList.addAll(newList)
        notifyDataSetChanged()
    }
}