package com.dzakyhdr.github.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dzakyhdr.github.data.Repository
import com.dzakyhdr.github.data.model.GithubUser
import com.dzakyhdr.github.data.model.Item
import com.dzakyhdr.github.util.NetworkResult
import kotlinx.coroutines.launch
import retrofit2.Response

class FollowersViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {
    private val _followers = MutableLiveData<NetworkResult<GithubUser>>()
    val followers: LiveData<NetworkResult<GithubUser>>
        get() = _followers

    fun getFollowers(username: String) = viewModelScope.launch {
        getFollowersSafeCall(username)
    }

    private suspend fun getFollowersSafeCall(username: String) {
        _followers.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.getFollowers(username)
                _followers.value = handleDetailUser(response)
            } catch (e: Exception) {
                _followers.value = NetworkResult.Error("User Not Found")
                Log.e("Error", e.message.toString())
            }
        } else {
            _followers.value = NetworkResult.Error("No Internet Connection")
        }
    }

    private fun handleDetailUser(response: Response<GithubUser>): NetworkResult<GithubUser>? {
        return when {
            response.message().toString().contains("timeout") -> {
                NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                NetworkResult.Error("API Key Limited")
            }
            response.isSuccessful -> {
                val followers = response.body()
                NetworkResult.Success(followers!!)
            }
            else -> {
                NetworkResult.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}