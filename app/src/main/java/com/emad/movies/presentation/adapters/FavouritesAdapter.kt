package com.emad.movies.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emad.movies.BuildConfig
import com.emad.movies.data.local.entities.FavouriteEntity
import com.emad.movies.databinding.MovieItemBinding
import com.emad.movies.domain.listeners.OnFavouriteSelected
import com.squareup.picasso.Picasso
import javax.inject.Inject

class FavouritesAdapter @Inject constructor(): RecyclerView.Adapter<FavouritesAdapter.MyViewHolder>() {
    val list = ArrayList<FavouriteEntity>()
    lateinit var onFavouriteSelected: OnFavouriteSelected
    open class MyViewHolder(private val mBinding: MovieItemBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        fun bind(movie: FavouriteEntity) {
            Picasso.get().load("${BuildConfig.IMAGE_BASE}${movie.movieImagePath}").into(mBinding.movieBosterIV)
            mBinding.movieNameTV.setText(movie.movieName)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            onFavouriteSelected.onFavSelected(list[position])
        }
    }

    override fun getItemCount(): Int = list.size

    fun submitList(newList: ArrayList<FavouriteEntity>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun submitFavListener(onFavouriteSelected: OnFavouriteSelected){
        this.onFavouriteSelected= onFavouriteSelected
    }
}