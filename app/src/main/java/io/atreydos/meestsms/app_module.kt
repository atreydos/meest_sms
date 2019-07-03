package io.atreydos.meestsms

import io.atreydos.meestsms.Constant.SERVER_URL
import io.atreydos.meestsms.data.api.MeestSmsApiService
import io.atreydos.meestsms.data.api.RestService
import io.atreydos.meestsms.interactor.SmsInteractor
import io.atreydos.meestsms.interactor.SmsInteractorImpl
import io.atreydos.meestsms.view.messaging.MessagingViewModel
import io.atreydos.meestsms.view.common.rx.ApplicationSchedulerProvider
import io.atreydos.meestsms.view.common.rx.SchedulerProvider
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object Constant {
    const val SERVER_URL = "SERVER_URL"
}

enum class Scope {
    MESSAGING
}

val appModule = module {

    //provided rx scheduler
    single { ApplicationSchedulerProvider() as SchedulerProvider }

    scope(named(Scope.MESSAGING.name)) {
        // provided web components
        scoped { RestService.create<MeestSmsApiService>(getProperty(SERVER_URL)) }
        // provided SMS interactor
        scoped { SmsInteractorImpl(get()) as SmsInteractor }

        viewModel { MessagingViewModel(get(), get()) }
    }


}