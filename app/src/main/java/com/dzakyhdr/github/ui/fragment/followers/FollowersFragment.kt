package com.dzakyhdr.github.ui.fragment.followers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.dzakyhdr.github.R
import com.dzakyhdr.github.adapters.FollowersAdapter
import com.dzakyhdr.github.data.model.Item
import com.dzakyhdr.github.util.NetworkResult
import com.dzakyhdr.github.viewmodels.FollowersViewModel
import kotlinx.android.synthetic.main.fragment_followers.*
import kotlinx.android.synthetic.main.layout_loading.*


class FollowersFragment : Fragment() {
    private val mAdapter by lazy { FollowersAdapter() }
    private lateinit var viewModel: FollowersViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(FollowersViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = arguments
        val myBundle: Item? = args?.getParcelable("userBundle")
//        setupRecyclerView()
//        requestFollowers(myBundle)
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

//    private fun requestFollowers(myBundle: Item?) {
//        myBundle?.login?.let { viewModel.getFollowers(it) }
//        viewModel.followers.observe(viewLifecycleOwner, {response ->
//            when (response) {
//                is NetworkResult.Success -> {
//                    showLoading(false)
//                    val followers = response.data
//                    followers?.let { mAdapter.setData(it) }
//                }
//                is NetworkResult.Error -> {
//                    showLoading(false)
//                    Toast.makeText(
//                        requireContext(),
//                        response.message.toString(),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//                is NetworkResult.Loading -> {
//                    showLoading(true)
//                }
//            }
//        })
//    }
//
//
//    private fun setupRecyclerView() {
//        rvFollowers.adapter = mAdapter
//        rvFollowers.layoutManager = LinearLayoutManager(requireContext())
//    }
//
//    private fun showLoading(isShow: Boolean){
//        loadingContainer.visibility = if (isShow) View.VISIBLE else View.GONE
//    }


}
