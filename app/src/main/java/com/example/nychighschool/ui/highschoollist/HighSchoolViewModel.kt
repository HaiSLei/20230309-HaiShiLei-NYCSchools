package com.example.nychighschool.ui.highschoollist

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nychighschool.models.HighSchool
import com.example.nychighschool.repositories.SchoolRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class HighSchoolViewModel : ViewModel() {

    private val _schoolListResult: MutableLiveData<Response<List<HighSchool>>> by lazy {
        MutableLiveData()
    }

    val schoolListResult: LiveData<Response<List<HighSchool>>> = _schoolListResult

    fun getHighSchoolList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = SchoolRepository.getHighSchools()
                _schoolListResult.postValue(response)
            } catch(e: Exception) {
                val responseBody = e.message?.toResponseBody() ?: "unknown error".toResponseBody()
                Log.e(TAG, "getHighSchoolList: $responseBody")
                _schoolListResult.postValue(Response.error(900, responseBody))
            }
        }
    }
}