package com.example.nychighschool.repositories


import com.example.nychighschool.api.ApiService
import com.example.nychighschool.models.HighSchool
import com.example.nychighschool.models.SchoolSatInfo
import retrofit2.Response

object SchoolRepository {

    private val api by lazy {
        ApiService.sharedInstance
    }
    suspend fun getHighSchools(): Response<List<HighSchool>> {
        return api.getHighSchools()
    }

    suspend fun getSAT(dbn: String) : Response<List<SchoolSatInfo>>{
        return api.getSatInfo(dbn)
    }
}