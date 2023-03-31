package com.example.moweapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.moweapp.adapter.ImageAdapter
import com.example.moweapp.adapter.MovieAdapter
import com.example.moweapp.api.ApiUtils
import com.example.moweapp.databinding.ActivityMainBinding
import com.example.moweapp.model.Movie
import com.example.moweapp.model.MovieResponse
import com.example.moweapp.utils.Constants.API_KEY
import com.example.moweapp.utils.Constants.IMAGE_BASE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(){

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }








}