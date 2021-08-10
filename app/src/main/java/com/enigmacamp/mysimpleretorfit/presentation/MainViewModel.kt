package com.enigmacamp.mysimpleretorfit.presentation

import androidx.lifecycle.*
import com.enigmacamp.mysimpleretorfit.data.remote.response.PostResponse
import com.enigmacamp.mysimpleretorfit.repository.PostRepository
import com.enigmacamp.mysimpleretorfit.util.Resource
import com.enigmacamp.mysimpleretorfit.util.ResourceStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(private val repo: PostRepository) : ViewModel() {
    private var _postLiveData = MutableLiveData<Resource<List<PostResponse>>>()
    val postLiveData: LiveData<Resource<List<PostResponse>>>
        get() = _postLiveData

    fun getPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            _postLiveData.postValue(Resource.loading())
            val response = repo.getPosts()
            if (response != null) {
                val responseStatus = response.status
                val responseMessage = response.message
                val responseData = response.data
                _postLiveData.postValue(Resource.success(data = responseData))
            } else {
                _postLiveData.postValue(Resource.error(message = "Oops...something went wrong"))
            }

        }
    }
}