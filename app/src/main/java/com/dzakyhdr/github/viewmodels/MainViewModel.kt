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

class MainViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    private val _userResponse = MutableLiveData<NetworkResult<GithubUser>>()
    val userResponse: LiveData<NetworkResult<GithubUser>>
        get() = _userResponse

    fun getAllUser(searchQuery: String) = viewModelScope.launch {
        getAllUserSafeCall(searchQuery)
    }


    private suspend fun getAllUserSafeCall(searchQuery: String) {
        _userResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.getAllUser(searchQuery)
                _userResponse.value = handleGithubUser(response)
            } catch (e: Exception) {
                _userResponse.value = NetworkResult.Error("User Not Found")
                Log.e("Error", e.message.toString())
            }
        } else {
            _userResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }

    private fun handleGithubUser(response: Response<GithubUser>): NetworkResult<GithubUser>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited")
            }
            response.body()!!.items.isNullOrEmpty() -> {
                return NetworkResult.Error("Github User Not Found")
            }
            response.isSuccessful -> {
                val githubUser = response.body()
                return NetworkResult.Success(githubUser!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
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