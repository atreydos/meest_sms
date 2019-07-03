package io.atreydos.meestsms.view.common.annotation

import androidx.annotation.LayoutRes


@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class Layout(@LayoutRes val id: Int)
