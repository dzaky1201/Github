package com.dzakyhdr.github.ui.fragment.Detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import coil.load
import com.dzakyhdr.github.R
import com.dzakyhdr.github.adapters.PagerAdapter
import com.dzakyhdr.github.databinding.FragmentDetailBinding
import com.dzakyhdr.github.ui.fragment.followers.FollowersFragment
import com.dzakyhdr.github.ui.fragment.following.FollowingFragment
import com.dzakyhdr.github.util.NetworkResult
import com.dzakyhdr.github.viewmodels.DetailViewModel
import com.dzakyhdr.github.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.layout_loading.*


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var detailViewModel: DetailViewModel
    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        requestDetail(args)
        val fragments = ArrayList<Fragment>()
        fragments.add(FollowersFragment())
        fragments.add(FollowingFragment())

        val titles = ArrayList<String>()
        titles.add("Followers")
        titles.add("Following")

        val resultBundle = Bundle()
        Log.d("cekBundle", resultBundle.toString())
        resultBundle.putParcelable("userBundle", args.itemResult)

        val adapter = PagerAdapter(
            resultBundle,
            fragments,
            titles,
            parentFragmentManager
        )

        binding.viewPager.adapter = adapter
        binding.tabs.setupWithViewPager(binding.viewPager)

        return binding.root
    }

    private fun requestDetail(args: DetailFragmentArgs) {
        detailViewModel.getDetailUser(args.itemResult.login)
        detailViewModel.userResponseDetail.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    val userDetail = response.data
                    binding.imageView.load(userDetail?.avatarUrl) {
                        crossfade(600)
                        error(R.drawable.ic_broken)
                    }
                    binding.txtFullname.text = userDetail?.name
                    binding.txtFollowers.text = userDetail?.followers.toString()
                    binding.txtFollowing.text = userDetail?.following.toString()
                    binding.txtRepository.text = userDetail?.repository.toString()

                }

                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(),response.message, Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading -> {

                }
            }
        })
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}