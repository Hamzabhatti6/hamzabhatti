package com.xisys.hamzabhatti.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.xisys.hamzabhatti.networks.Resource
import com.xisys.hamzabhatti.repo.Repository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository = Repository(application.applicationContext)
    fun getAllFeeds() = liveData(Dispatchers.IO) {
        emit(Resource.loading(response = null))
        try {
            emit(Resource.success(response = repository.getFeeds()))
        } catch (exception: Exception) {
            emit(Resource.error(response = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}