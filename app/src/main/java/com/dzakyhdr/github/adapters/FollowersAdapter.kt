package com.dzakyhdr.github.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dzakyhdr.github.R
import com.dzakyhdr.github.data.model.GithubUser
import com.dzakyhdr.github.data.model.Item
import com.dzakyhdr.github.util.UserDiffUtil
import kotlinx.android.synthetic.main.followers_row_layout.view.*

class FollowersAdapter : RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {

    private var user = emptyList<Item>()

    fun debug(){
        Log.d("getData", user.toString())
    }

    inner class FollowersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Item) {
            with(itemView) {
                img_user.load(item.avatarUrl)
                tvUsername.text = item.login
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
        return FollowersViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.followers_row_layout, parent, false)
        )
    }

    override fun getItemCount(): Int = user.size

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        holder.bind(user[position])
    }

    fun setData(newData: GithubUser){
        val userDiffUtil = UserDiffUtil(user, newData.items)
        val diffUtilItem = DiffUtil.calculateDiff(userDiffUtil)
        user = newData.items
        diffUtilItem.dispatchUpdatesTo(this)
    }

}