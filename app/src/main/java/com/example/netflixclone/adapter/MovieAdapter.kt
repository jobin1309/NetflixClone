package com.example.netflixclone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.netflixclone.MovieDiffUtil
import com.example.netflixclone.R
import com.example.netflixclone.databinding.MovieRowLayoutBinding
import com.example.netflixclone.model.MovieResponse
import com.example.netflixclone.model.MoviesResult
import com.example.netflixclone.ui.MovieFragmentDirections
import com.example.netflixclone.utils.Constants

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {


    private var movieList = emptyList<MoviesResult>()

    class ViewHolder(private val binding: MovieRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {



        fun bind(item: MoviesResult, item2: MoviesResult) {
            binding.apply {
                movieNameText.text = item.title
                movieNameText2.text = item2.title
                MovieDateText.text = item.releaseDate
                MovieDateText2.text = item2.releaseDate

                val likesInThousand1 = item.voteCount.toDouble() / 1000
                val formattedValue =  likesInThousand1

                val likesInThousand2 = item2.voteCount.toDouble() / 1000
                val formattedValue2 = likesInThousand2


                likesText.text = "${formattedValue}K"
                likesText2.text = "${formattedValue2}K"

                val moviePosterURL = Constants.POSTER_BASE_URL + item.posterPath
                firstImageView.load(moviePosterURL) {
                    crossfade(true)
                    placeholder(R.drawable.ic_launcher_foreground)
                }
                val moviePosterURL2 = Constants.POSTER_BASE_URL + item2.posterPath
                SecondimageView.load(moviePosterURL2) {
                    crossfade(true)
                    placeholder(R.drawable.ic_launcher_foreground)
                }


            }


        }


        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieRowLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return movieList.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentMovie1 = movieList[position * 2]
        var currentMovie2 =
            if (position * 2 + 1 < movieList.size) movieList[position * 2 + 1] else null
        if (currentMovie2 != null && currentMovie1 != null) {
            holder.bind(currentMovie1, currentMovie2)
        }

        holder.itemView.findViewById<ConstraintLayout>(R.id.constraintLayout).setOnClickListener {

            val action =
                MovieFragmentDirections.actionMovieFragmentToDetailFragment(currentMovie1.id)
            holder.itemView.findNavController().navigate(action)
        }

        holder.itemView.findViewById<ConstraintLayout>(R.id.constraintLayout2).setOnClickListener {

            val action =
                MovieFragmentDirections.actionMovieFragmentToDetailFragment(currentMovie2!!.id)
            holder.itemView.findNavController().navigate(action)

        }

    }

//    private val differCallback = object: DiffUtil.ItemCallback<Result>() {
//        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
//            return oldItem == newItem
//        }
//
//    }
//
//    val differ = AsyncListDiffer(this, differCallback)


    fun setData(newData: MovieResponse) {
        var moviesDiffUtil = MovieDiffUtil(movieList, newData.results)
        var diffUtilResult = DiffUtil.calculateDiff(moviesDiffUtil)
        movieList = newData.results
        diffUtilResult.dispatchUpdatesTo(this)

    }
}