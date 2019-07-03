package io.atreydos.meestsms.interactor

import io.atreydos.meestsms.interactor.helper.MeestSms
import io.atreydos.meestsms.data.api.MeestSmsApiService
import io.reactivex.Completable

interface SmsInteractor {
    fun sendSms(recipientNumber: String, message: String): Completable
}

class SmsInteractorImpl(private val meestSmsApiService: MeestSmsApiService) : SmsInteractor {

    override fun sendSms(recipientNumber: String, message: String): Completable {
        val urlQuery = MeestSms.buildSendMessageRequestUrl(recipientNumber, message)
        return meestSmsApiService.sendSms(urlQuery)
            .map { it.toLongOrNull() ?: throw IllegalStateException("Something way wrong. ResponseData{$it}") }
            .toCompletable()
    }
}
