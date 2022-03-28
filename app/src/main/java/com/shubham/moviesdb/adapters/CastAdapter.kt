package com.shubham.moviesdb.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shubham.moviesdb.databinding.ItemCastBinding
import com.shubham.moviesdb.response.Cast

class CastAdapter(private val callback:CastAdapterCallback) : RecyclerView.Adapter<CastAdapter.ViewHolder>() {

    private var castList: List<Cast>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCastBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(castList?.get(position),callback)
    }

    override fun getItemCount(): Int {
        return if (castList != null) {
            castList!!.size
        } else {
            0
        }

    }

    fun setData(castList: List<Cast>) {
        this.castList = castList
    }


    class ViewHolder(private val binding: ItemCastBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cast: Cast?, callback: CastAdapterCallback) {
            binding.castName.text = cast?.name
            binding.castCharacter.text = cast?.character
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w500" + cast?.profilePath)
                .override(120, 120)
                .into(binding.castPhoto)
            binding.root.setOnClickListener {
                cast?.id?.let { callback.onCastClicked(it) }
                Log.d("castadapter","${cast?.castId}")
            }

        }

    }
}
interface CastAdapterCallback{
    fun onCastClicked(castId: Int)
}
