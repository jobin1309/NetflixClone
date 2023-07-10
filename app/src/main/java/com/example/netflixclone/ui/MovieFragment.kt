package com.example.netflixclone.ui

import android.os.Bundle
import android.provider.SyncStateContract.Constants
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.netflixclone.R
import com.example.netflixclone.adapter.MovieAdapter
import com.example.netflixclone.databinding.FragmentMovie2Binding
import com.example.netflixclone.utils.Constants.API_KEY
import com.example.netflixclone.utils.NetworkResult
import com.example.netflixclone.viewModel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieFragment : Fragment(), SearchView.OnQueryTextListener{

    private lateinit var binding: FragmentMovie2Binding
    private val mAdapter by lazy { MovieAdapter() }
    private val viewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovie2Binding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setHasOptionsMenu(true)
        recyclerView()
        requestApiData()

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_bar, menu)

        val searchItem = menu.findItem(R.id.Menu_search)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)

    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchApiData(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun searchApiData(query: String) {

        try {
            viewModel.getSearchMovies(query)

            viewModel.searchMovieResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        response?.data.let {
                            if (it != null) {
                                mAdapter.setData(it)
                            }
                        }
                    }

                    else -> {
                        Log.d("Network api", " Error")
                    }
                }
            }
        }
        catch (e:Exception) {
            Log.d("SearchApi", "Error")
        }
    }


    private fun recyclerView() {
        binding.recyclerView.adapter = mAdapter;
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun requestApiData() {
        try {
            viewModel.fetchPopularMovies(1)
            Log.d("Api", "API request initiated")

            viewModel.movieResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        response.data?.let {
                            mAdapter.setData(it)
                        }
                    }

                    else -> {
                        Log.d("NetworkResultClass", "ERROR/LOADING")
                    }
                }

            }
        } catch (e: Exception) {
            Log.d("Api", "Error: ${e.message}")
        }
    }
}