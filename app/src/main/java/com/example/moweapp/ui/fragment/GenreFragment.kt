package com.example.moweapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.example.moweapp.R
import com.example.moweapp.adapter.CategoryMovieAdapter
import com.example.moweapp.adapter.NameAdapter
import com.example.moweapp.api.ApiUtils
import com.example.moweapp.databinding.FragmentDetailBinding
import com.example.moweapp.databinding.FragmentGenreBinding
import com.example.moweapp.model.GenreResponse
import com.example.moweapp.model.Movie
import com.example.moweapp.model.MovieResponse
import com.example.moweapp.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class GenreFragment : Fragment(),NameAdapter.OnItemClickListener {
    private val utils = ApiUtils.getApi()
    private val adapter = CategoryMovieAdapter()
    private lateinit var searchView: SearchView
    private var movieList = kotlin.collections.ArrayList<Movie>()
    private var _binding : FragmentGenreBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenreBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.rvAll.adapter = adapter
        setDialog(true)
        getAllMovies()

        searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })





    }


    private fun filterList(query: String?) {
        if(query != null){
            val filteredList = kotlin.collections.ArrayList<Movie>()
            for(i in movieList){
                if (i.title.toLowerCase(Locale.ROOT).contains(query)){
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()){
                Toast.makeText(requireContext(),"No data found", Toast.LENGTH_SHORT).show()
            }else{
                adapter.setFilteredList(filteredList)
            }
        }

    }

    private fun getAllMovies() {
        utils.getMovieByGenre(Constants.API_KEY,"").enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                setDialog(false)
                if(response.isSuccessful){
                    response.body().let {
                        adapter.updateList(it?.movies as ArrayList<Movie>)
                        movieList = it.movies as ArrayList<Movie>
                    }

                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                setDialog(false)
            }
        })

        utils.getGenreList(Constants.API_KEY).enqueue(object : Callback<GenreResponse> {
            override fun onResponse(call: Call<GenreResponse>, response: Response<GenreResponse>) {
                if (response.isSuccessful){
                    response.body().let {
                        Log.e("neticeGenreList",it?.genres.toString())
                        binding.rvGenre.adapter = it?.genres?.let { it1 -> NameAdapter(this@GenreFragment,it1) }

                    }
                }
            }

            override fun onFailure(call: Call<GenreResponse>, t: Throwable) {
                Toast.makeText(requireContext(),t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })


    }


    override fun onItemClick(genreId: String) {
        setDialog(true)
        utils.getMovieByGenre(Constants.API_KEY,genreId).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                setDialog(false)
                if(response.isSuccessful){
                    response.body().let {
                        adapter.updateList(it?.movies as ArrayList<Movie>)
                        movieList=it.movies as ArrayList<Movie>
                    }

                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                setDialog(false)
            }
        })
    }

    private fun setDialog(bool:Boolean){
        val pb = binding.progressBar
        if(bool){
            pb.visibility = View.VISIBLE
        }else{
            pb.visibility = View.INVISIBLE
        }
    }






    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}