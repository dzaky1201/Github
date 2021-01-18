package com.dzakyhdr.github.data.model


import com.google.gson.annotations.SerializedName

data class GithubUser(
    @SerializedName("items")
    val items: List<Item>
)

