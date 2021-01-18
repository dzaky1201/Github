package com.dzakyhdr.github.data.network

import com.dzakyhdr.github.BuildConfig
import com.dzakyhdr.github.data.model.GithubUser
import com.dzakyhdr.github.data.model.Item
import retrofit2.Response
import retrofit2.http.*

interface GithubApi {

    @GET("/search/users")
    @Headers("Authorization: token ${BuildConfig.GITHUB_API_KEY}")
    suspend fun searchUser(
        @Query("q") query: String
    ): Response<GithubUser>

    @GET("/users/{username}")
    @Headers("Authorization: token ${BuildConfig.GITHUB_API_KEY}")
    suspend fun detailUser(
        @Path("username") username: String
    ): Response<Item>

    @GET("/users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.GITHUB_API_KEY}")
    suspend fun getFollowers(
        @Path("username") username: String
    ): Response<GithubUser>
}