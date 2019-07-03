package io.atreydos.meestsms.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Url

interface MeestSmsApiService {

    @GET
//    @GET("/services/sms/add_msg.php")
    @Headers("User-Agent: Meest SMS")
    fun sendSms(@Url url: String): Single<String>

}
