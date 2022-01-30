package com.emad.movies.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emad.movies.data.model.MovieReviews
import com.emad.movies.databinding.ReviewItemBinding
import javax.inject.Inject

class ReviewsAdapter @Inject constructor(): RecyclerView.Adapter<ReviewsAdapter.MyViewHolder>() {
    val list = ArrayList<MovieReviews.Result>()

    open class MyViewHolder(private val mBinding: ReviewItemBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        fun bind(review: MovieReviews.Result) {
            mBinding.review= review
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ReviewItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun submitList(newList: ArrayList<MovieReviews.Result>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}