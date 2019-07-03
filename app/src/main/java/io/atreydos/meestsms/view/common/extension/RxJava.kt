package io.atreydos.meestsms.view.common.extension

import io.atreydos.meestsms.view.common.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Single


/**
 * Use SchedulerProvider configuration for Single
 */
fun <T> Single<T>.with(schedulerProvider: SchedulerProvider): Single<T> = observeOn(schedulerProvider.ui()).subscribeOn(schedulerProvider.io())

fun Completable.with(schedulerProvider: SchedulerProvider): Completable = observeOn(schedulerProvider.ui()).subscribeOn(schedulerProvider.io())