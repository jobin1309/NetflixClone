package com.example.netflixclone.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.netflixclone.R
import com.example.netflixclone.databinding.FragmentDetailBinding
import com.example.netflixclone.utils.Constants
import com.example.netflixclone.utils.Constants.POSTER_BASE_URL
import com.example.netflixclone.utils.NetworkResult
import com.example.netflixclone.viewModel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log


@AndroidEntryPoint
class DetailFragment : Fragment() {


    private val args by navArgs<DetailFragmentArgs>()
    private val mViewModel: MovieViewModel by viewModels()
    private lateinit var binding: FragmentDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var movieID = args.MovieID

        mViewModel.getFetchMovieDetails(movieID)

        mViewModel.movieDetailsResponse.observe(viewLifecycleOwner) { response ->
            val moviePosterPath = POSTER_BASE_URL + response.data?.posterPath
            binding.imageMovie.load(moviePosterPath) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
            }
            binding.backgroundImage.load(moviePosterPath) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
            }
            binding.apply {
                movieNameText.text = response.data?.title
                MovieDateText.text = response.data?.releaseDate
                RevenueText.text = response.data?.revenue.toString()
                tagLineText.text = response.data?.tagline
                ratingText.text = response.data?.vote_average.toString()
                budgetText.text = response.data?.budget.toString()
                overViewText.text = response.data?.overview.toString()

            }

        }
    }

}