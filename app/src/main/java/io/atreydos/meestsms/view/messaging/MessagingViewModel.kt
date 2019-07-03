package io.atreydos.meestsms.view.messaging

import io.atreydos.meestsms.view.common.extension.with
import androidx.lifecycle.MutableLiveData
import io.atreydos.meestsms.interactor.SmsInteractor
import io.atreydos.meestsms.view.common.rx.SchedulerProvider
import io.atreydos.meestsms.view.common.AbstractViewModel
import io.atreydos.meestsms.view.common.SingleLiveEvent

class MessagingViewModel(private val smsInteractor: SmsInteractor, private val scheduler: SchedulerProvider) :
    AbstractViewModel() {

    val searchEvent = SingleLiveEvent<SendingSmsEvent>()
    val uiData = MutableLiveData<SendSmsUIModel>()

    fun sendSms(recipientNumber: String, message: String) {
        launch {
            uiData.value = SendSmsUIModel(recipientNumber, message)
            searchEvent.value = SendingSmsEvent(isLoading = true)

            smsInteractor.sendSms(recipientNumber, message)
                .with(scheduler)
                .subscribe(
                    {
                        searchEvent.postValue(SendingSmsEvent(isSuccess = true))
                    },
                    { err ->
                        searchEvent.postValue(SendingSmsEvent(error = err))
                    })
        }
    }
}

data class SendSmsUIModel(
    val recipientNumber: String? = null,
    val message: String? = null
)
data class SendingSmsEvent(val isLoading: Boolean = false, val isSuccess: Boolean = false, val error: Throwable? = null)