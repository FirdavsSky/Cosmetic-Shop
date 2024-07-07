package com.firdavs.android.cosmeticshop.Activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.firdavs.android.cosmeticshop.Adapter.CategoryAdapter
import com.firdavs.android.cosmeticshop.Adapter.RecommendationAdapter
import com.firdavs.android.cosmeticshop.Adapter.SliderAdapter
import com.firdavs.android.cosmeticshop.Model.SliderModel
import com.firdavs.android.cosmeticshop.R
import com.firdavs.android.cosmeticshop.ViewModel.MainViewModel
import com.firdavs.android.cosmeticshop.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private val viewModel = MainViewModel()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBanners()
        initCategory()
        initRecommended()
    }

    private fun initBanners() {
        binding.progressBarBanner.visibility = View.VISIBLE
        viewModel.banner.observe(this, Observer {
            banners(it)
            binding.progressBarBanner.visibility = View.GONE
        })
        viewModel.loadBanner()
    }

    private fun banners(it: List<SliderModel>) {
        binding.viewPagerSlider.adapter = SliderAdapter(it, binding.viewPagerSlider)
        binding.viewPagerSlider.clipToPadding = false
        binding.viewPagerSlider.clipChildren = false
        binding.viewPagerSlider.offscreenPageLimit = 3
        binding.viewPagerSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
        }

        binding.viewPagerSlider.setPageTransformer(compositePageTransformer)
        if (it.size > 1){
            binding.dotIndicator.visibility = View.VISIBLE
            binding.dotIndicator.attachTo(binding.viewPagerSlider)
        }
    }

    private fun initCategory(){
        binding.progressBarCategory.visibility = View.VISIBLE
        viewModel.category.observe(this, Observer {
            binding.viewCategory.layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL,false)
            binding.viewCategory.adapter = CategoryAdapter(it)
            binding.progressBarCategory.visibility = View.GONE
        })
        viewModel.loadCategory()
    }

    private fun initRecommended(){
        binding.progressBarRecommendation.visibility = View.VISIBLE
        viewModel.recommend.observe(this, Observer {
            binding.viewRecommendation.layoutManager = GridLayoutManager(this@MainActivity,2)
            binding.viewRecommendation.adapter = RecommendationAdapter(it)
            binding.progressBarRecommendation.visibility = View.GONE
        })
        viewModel.loadRecommended()
    }
}