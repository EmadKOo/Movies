package com.emad.movies.presentation.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.emad.movies.BuildConfig
import com.emad.movies.data.local.entities.MovieEntity
import com.emad.movies.data.model.PopularMovies
import com.emad.movies.databinding.MovieItemBinding
import com.emad.movies.domain.listeners.OnMovieSelected
import com.squareup.picasso.Picasso
import dagger.hilt.android.qualifiers.ApplicationContext

class MovieAdapter(private val onMovieSelected: OnMovieSelected, private val context: Context): PagingDataAdapter<MovieEntity , MovieAdapter.MyViewHolder>(DIFFUTIL) {

    inner class MyViewHolder(private val mBinding: MovieItemBinding): RecyclerView.ViewHolder(mBinding.root){
        fun bind(movie: MovieEntity){
            //Picasso.get().load("${BuildConfig.IMAGE_BASE}${movie.poster_path}").into(mBinding.movieBosterIV)
            Glide.with(context)
                .asBitmap()
                .load("${BuildConfig.IMAGE_BASE}${movie.movieImagePath}")
                .centerCrop()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(object : CustomTarget<Bitmap>(){
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        mBinding.movieBosterIV.setImageBitmap(resource)
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {
                        mBinding.movieBosterIV.setImageDrawable(placeholder)
                    }
                })
            mBinding.movieNameTV.setText(movie.movieName)
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
        private val DIFFUTIL = object : DiffUtil.ItemCallback<MovieEntity>(){
            override fun areItemsTheSame(
                oldItem: MovieEntity,
                newItem: MovieEntity,
            ): Boolean {
                return oldItem.movieID == newItem.movieID
            }

            override fun areContentsTheSame(
                oldItem: MovieEntity,
                newItem: MovieEntity,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}