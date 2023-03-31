package com.example.moweapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moweapp.adapter.CastAdapter
import com.example.moweapp.api.ApiUtils
import com.example.moweapp.databinding.FragmentDetailBinding
import com.example.moweapp.model.CastResponse
import com.example.moweapp.model.DetailResponse
import com.example.moweapp.model.ResponseBody
import com.example.moweapp.utils.Constants
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailFragment : Fragment() {

    private var _binding : FragmentDetailBinding?=null
    private val binding get() = _binding!!
    private val utils = ApiUtils.getApi()
    private val cAdapter = CastAdapter()
    private val args : DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = args.id
        setAdapter()
        getData(id)


        binding.button.setOnClickListener {
            val rate = binding.etRate.text.toString().toDouble()
            rateMovie(id,rate)
        }

        binding.ivBack.setOnClickListener{
            findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToHomeFragment())
        }

    }



    private fun rateMovie(id: Int,rate: Double) {
        utils.rateMovie(id,rate, Constants.API_KEY).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Toast.makeText(requireContext(),"Movie Rated", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(requireContext(),t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getData(id: Int) {

        utils.getDetail(id, Constants.API_KEY).enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                if(response.isSuccessful){
                    response.body()?.let { setData(it)}
                }
            }
            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Toast.makeText(requireContext(),t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })



        utils.getCast(id, Constants.API_KEY).enqueue(object : Callback<CastResponse> {
            override fun onResponse(call: Call<CastResponse>, response: Response<CastResponse>) {
                response.body().let {
                    cAdapter.updateList(it!!.cast)
                }
            }

            override fun onFailure(call: Call<CastResponse>, t: Throwable) {
                Toast.makeText(requireContext(),t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun setAdapter() {
        binding.rvCast.adapter = cAdapter
    }

    private fun setData(response: DetailResponse) {
        var genre = "| "

        with(binding){
            movieDB = response

            Picasso.get().load(Constants.IMAGE_BASE +response.posterPath).into(imageView)
            ratingBar.setProgress(response.voteAverage.toInt(),true)
            tvRuntime.text = response.runtime.toString() + "min"
            tvRating.text = "%.1f | ${response.voteCount} ".format(response.voteAverage)

            for (i in response.genres){
                genre += i.name + " | "
            }

            tvGenres.text = genre


        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}