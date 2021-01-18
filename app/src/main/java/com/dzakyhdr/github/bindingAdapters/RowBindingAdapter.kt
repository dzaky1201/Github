package com.dzakyhdr.github.bindingAdapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.dzakyhdr.github.R
import com.dzakyhdr.github.data.model.GithubUser
import com.dzakyhdr.github.data.model.Item
import com.dzakyhdr.github.ui.fragment.Home.HomeUserFragmentDirections
import com.dzakyhdr.github.util.NetworkResult

class RowBindingAdapter {

    companion object {

        @BindingAdapter("homeToDetailFragment")
        @JvmStatic
        fun homeToDetailFragment(homeHolderRowLayout: ConstraintLayout, item: Item) {
            homeHolderRowLayout.setOnClickListener {
                try {
                    val action =
                        HomeUserFragmentDirections.actionHomeUserFragmentToDetailFragment(item)
                    homeHolderRowLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("homeClicked", e.toString())
                }
            }
        }

        @BindingAdapter("readApi", requireAll = true)
        @JvmStatic
        fun errorImageVisibility(
            imageView: ImageView,
            apiResponse: NetworkResult<GithubUser>?
        ) {
            if (apiResponse is NetworkResult.Error) {
                imageView.visibility = View.VISIBLE
            } else if (apiResponse is NetworkResult.Loading) {
                imageView.visibility = View.INVISIBLE
            } else if (apiResponse is NetworkResult.Success) {
                imageView.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl) {
                crossfade(600)
                error(R.drawable.ic_broken)
            }
        }
    }
}