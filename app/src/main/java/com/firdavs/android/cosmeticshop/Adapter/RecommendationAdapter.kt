package com.firdavs.android.cosmeticshop.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.firdavs.android.cosmeticshop.Model.ItemsModel
import com.firdavs.android.cosmeticshop.R
import com.firdavs.android.cosmeticshop.databinding.ViewholderRecommendBinding

class RecommendationAdapter(val items: MutableList<ItemsModel>)
        : RecyclerView.Adapter<RecommendationAdapter.ViewHolder>() {

    private var context: Context? = null

    class ViewHolder(val binding: ViewholderRecommendBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendationAdapter.ViewHolder {
        context = parent.context
        val binding =
            ViewholderRecommendBinding.inflate(LayoutInflater.from(context), parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendationAdapter.ViewHolder, position: Int) {
        holder.binding.titleTxt.text = items[position].title

        val picUrl = items[position].picUrl
        Log.d("RecommendationAdapter", "Loading image URL: $picUrl")

        holder.binding.priceTxt.text = "$" + items[position].price.toString()
        holder.binding.ratingTxt.text = items[position].rating.toString()

        Glide.with(holder.itemView.context)
            .load(items[position].picUrl)
            .apply(RequestOptions.centerCropTransform())
            .error(R.drawable.error_image) // Замените на ваш ресурс изображения для ошибок
            .into(holder.binding.pic)

    }

    override fun getItemCount(): Int = items.size
}