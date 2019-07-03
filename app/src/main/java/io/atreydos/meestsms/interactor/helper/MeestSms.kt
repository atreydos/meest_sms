package io.atreydos.meestsms.interactor.helper

import android.net.Uri
import io.atreydos.meestsms.view.common.extension.toMD5

object MeestSms {

    private const val API_SIGN_SALT = "eMt)jK$8&M&2).B-"
    private const val API_USER_TYPE = "user_mob"

    fun buildSendMessageRequestUrl(recipientNumber: String, message: String): String {
        val dataUriString = Uri.Builder()
            .appendQueryParameter("phone", recipientNumber)
            .appendQueryParameter("text", message)
            .appendQueryParameter("user", API_USER_TYPE)
            .build()

        val sign = "${dataUriString.toString().replace("%20", "+").substring(1)}$API_SIGN_SALT".toMD5()

        return dataUriString.buildUpon()
//            .scheme("https")
//            .authority("apii.meest-group.com")
            .appendPath("services")
            .appendPath("sms")
            .appendPath("add_msg.php")
            .appendQueryParameter("sign", sign)
            .build().toString().replace("%20", "+")
    }
}
