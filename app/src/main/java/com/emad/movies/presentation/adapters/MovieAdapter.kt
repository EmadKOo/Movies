package com.emad.movies.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.emad.movies.BuildConfig
import com.emad.movies.data.model.PopularMovies
import com.emad.movies.databinding.MovieItemBinding
import com.emad.movies.domain.listeners.OnMovieSelected
import com.squareup.picasso.Picasso

class MovieAdapter(private val onMovieSelected: OnMovieSelected): PagingDataAdapter<PopularMovies.Result , MovieAdapter.MyViewHolder>(DIFFUTIL) {

    inner class MyViewHolder(private val mBinding: MovieItemBinding): RecyclerView.ViewHolder(mBinding.root){
        fun bind(movie: PopularMovies.Result){
            Picasso.get().load("${BuildConfig.IMAGE_BASE}${movie.poster_path}").into(mBinding.movieBosterIV)
            mBinding.movieNameTV.setText(movie.title)
            mBinding.movieViewHolder.setOnClickListener {
                onMovieSelected.movieSelected(movie)
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem= getItem(position)
        if (currentItem != null)
            holder.bind(currentItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val mBinding= MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(mBinding)
    }

    companion object{
        private val DIFFUTIL = object : DiffUtil.ItemCallback<PopularMovies.Result>(){
            override fun areItemsTheSame(
                oldItem: PopularMovies.Result,
                newItem: PopularMovies.Result,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PopularMovies.Result,
                newItem: PopularMovies.Result,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}