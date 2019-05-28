package com.kvelasco.openmovie.trending.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kvelasco.openmovie.R
import com.kvelasco.openmovie.di.injector
import com.kvelasco.openmovie.di.viewModel
import com.kvelasco.openmovie.trending.TrendingRecyclerAdapter

class TrendingMoviesFragment: Fragment() {

    private val moviesViewModel: TrendingMoviesViewModel by viewModel {
        requireActivity().injector.trendingMoviesViewModel
    }

    private lateinit var recyclerView: RecyclerView

    private val adapter: TrendingRecyclerAdapter =
        TrendingRecyclerAdapter()

    companion object {
        fun newInstance(): TrendingMoviesFragment =
            TrendingMoviesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_trending_movies, container, false)
        recyclerView = view.findViewById(R.id.trending_recycler)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        moviesViewModel
        moviesViewModel.uiState.observe(viewLifecycleOwner, Observer {
            if (it.result != null) {
                adapter.addTrending(it.result)
            }
        })
    }
}