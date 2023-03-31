package com.example.moweapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.moweapp.adapter.ImageAdapter
import com.example.moweapp.adapter.MovieAdapter
import com.example.moweapp.api.ApiUtils
import com.example.moweapp.databinding.FragmentHomeBinding
import com.example.moweapp.model.Movie
import com.example.moweapp.model.MovieResponse
import com.example.moweapp.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private val utils = ApiUtils.getApi()
    private val mAdapter = MovieAdapter()

    private var _binding : FragmentHomeBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.rv.adapter = mAdapter
        getData()

        binding.btnShow.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToGenreFragment())
        }


    }

    private fun getData() {
        utils.getReleaseMovies(Constants.API_KEY).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if(response.isSuccessful){
                    response.body().let { setViewPager(it) }
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Toast.makeText(requireContext(),t.localizedMessage, Toast.LENGTH_SHORT).show()

            }
        })

        utils.getPopularMovies(Constants.API_KEY).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                response.body()?.movies.let {
                    mAdapter.updateList(it as ArrayList<Movie>)
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Toast.makeText(requireContext(),t.localizedMessage, Toast.LENGTH_SHORT).show()

            }
        })


    }

    private fun setViewPager(it: MovieResponse?) {
        with(binding){
            viewPager.adapter = ImageAdapter(it?.movies as ArrayList<Movie>,binding.viewPager)
            viewPager.offscreenPageLimit = 3
            viewPager.clipToPadding = false

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}