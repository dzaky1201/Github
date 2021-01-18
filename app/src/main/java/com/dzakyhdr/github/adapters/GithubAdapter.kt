package com.dzakyhdr.github.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dzakyhdr.github.data.model.GithubUser
import com.dzakyhdr.github.data.model.Item
import com.dzakyhdr.github.databinding.HomeRowLayoutBinding
import com.dzakyhdr.github.util.UserDiffUtil

class GithubAdapter : RecyclerView.Adapter<GithubAdapter.MyViewHolder>() {

    private var user = emptyList<Item>()

    class MyViewHolder(private val binding: HomeRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HomeRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentUser = user[position]
        holder.bind(currentUser)
    }

    override fun getItemCount(): Int {
        return user.size
    }

    fun setData(newData: GithubUser){
        val userDiffUtil = UserDiffUtil(user, newData.items)
        val diffUtilItem = DiffUtil.calculateDiff(userDiffUtil)
        user = newData.items
        diffUtilItem.dispatchUpdatesTo(this)
    }

}