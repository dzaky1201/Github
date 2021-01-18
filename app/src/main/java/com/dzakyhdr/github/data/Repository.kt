package com.dzakyhdr.github.data

import com.dzakyhdr.github.data.model.GithubUser
import com.dzakyhdr.github.data.model.Item
import com.dzakyhdr.github.data.network.GithubApi
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Response
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    private val githubApi: GithubApi
) {

    suspend fun getAllUser(searchQuery: String): Response<GithubUser> {
        return githubApi.searchUser(searchQuery)
    }

    suspend fun getDetailUser(detailUsername: String): Response<Item> {
        return githubApi.detailUser(detailUsername)
    }

    suspend fun getFollowers(username: String): Response<GithubUser>{
        return githubApi.getFollowers(username)
    }




}
