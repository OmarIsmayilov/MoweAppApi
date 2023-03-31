package com.example.moweapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.moweapp.databinding.ImageContainerBinding
import com.example.moweapp.model.Movie
import com.example.moweapp.ui.fragment.HomeFragmentDirections
import com.example.moweapp.utils.Constants.IMAGE_BASE
import com.squareup.picasso.Picasso

class ImageAdapter(private val movieList: ArrayList<Movie>, private val viewPager2: ViewPager2) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(val binding: ImageContainerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = ImageContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val movie = movieList[position]

        setData(movie, holder)


        if (position == movieList.size - 1) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    private val runnable = Runnable {
        movieList.addAll(movieList)
        notifyDataSetChanged()
    }

    private fun setData(movie: Movie, holder: ImageViewHolder) {
        with(holder.binding) {

            Picasso.get().load(IMAGE_BASE + movie.backdropPath).into(imageView)
            tvImage.text = movie.title
            tvVoteAverage.text = "%.1f".format(movie.voteAverage)
            rbImage.setProgress(movie.voteAverage.toInt(), false)
            imageView.setOnClickListener {
                Navigation.findNavController(it).navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(movie.id))
            }

        }
    }
}