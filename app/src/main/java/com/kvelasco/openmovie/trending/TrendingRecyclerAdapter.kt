package com.kvelasco.openmovie.trending

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kvelasco.openmovie.R

class TrendingRecyclerAdapter:  RecyclerView.Adapter<TrendingRecyclerAdapter.ViewHolder>() {

    private val list: MutableList<TrendingUiModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.trending_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addTrending(list: List<TrendingUiModel>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val image: ImageView = itemView.findViewById(R.id.item_image)

        fun bind(trendingUiModel: TrendingUiModel) {
            Glide.with(image)
                .load(trendingUiModel.posterPath)
                .into(image)
        }
    }

}