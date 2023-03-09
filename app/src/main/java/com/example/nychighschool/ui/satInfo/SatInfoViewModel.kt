package com.example.nychighschool.ui.satInfo

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nychighschool.models.SchoolSatInfo
import com.example.nychighschool.repositories.SchoolRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class SatInfoViewModel: ViewModel() {

    private val _satResult: MutableLiveData<Response<List<SchoolSatInfo>>> by lazy {
        MutableLiveData()
    }

    val satResult: LiveData<Response<List<SchoolSatInfo>>> = _satResult

    fun getSatInfo(dbn : String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = SchoolRepository.getSAT(dbn)
                _satResult.postValue(response)
            } catch(e: Exception) {
                val responseBody = e.message?.toResponseBody() ?: "unknown error".toResponseBody()
                Log.e(TAG, "getHighSchoolList: $responseBody")
                _satResult.postValue(Response.error(900, responseBody))
            }
        }
    }
}