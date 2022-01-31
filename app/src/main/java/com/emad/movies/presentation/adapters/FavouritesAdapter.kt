package com.emad.movies.presentation.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.emad.movies.BuildConfig
import com.emad.movies.R
import com.emad.movies.data.local.entities.FavouriteEntity
import com.emad.movies.databinding.MovieItemBinding
import com.emad.movies.domain.listeners.OnFavouriteSelected
import com.squareup.picasso.Picasso
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FavouritesAdapter @Inject constructor(@ApplicationContext  val context: Context): RecyclerView.Adapter<FavouritesAdapter.MyViewHolder>() {
    val list = ArrayList<FavouriteEntity>()
    lateinit var onFavouriteSelected: OnFavouriteSelected
    open class MyViewHolder(private val mBinding: MovieItemBinding, val context: Context) :
        RecyclerView.ViewHolder(mBinding.root) {
        fun bind(movie: FavouriteEntity) {
            //Picasso.get().load("${BuildConfig.IMAGE_BASE}${movie.movieImagePath}").into(mBinding.movieBosterIV)
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
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            ),
            context
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