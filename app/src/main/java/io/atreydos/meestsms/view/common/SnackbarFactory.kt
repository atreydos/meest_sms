package io.atreydos.meestsms.view.common

import android.graphics.Color
import android.view.View
import androidx.annotation.ColorInt
import com.google.android.material.snackbar.Snackbar

object SnackbarFactory {

    @ColorInt private val ERROR_COLOR = Color.RED
    @ColorInt private val NORMAL_COLOR = Color.DKGRAY
    @ColorInt private val SUCCESS_COLOR = Color.parseColor("#388E3C")

    fun createSnackbar(parentView: View, text: String, length: Int, color: Int): Snackbar {
        val snackbar = Snackbar.make(parentView, text, length)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(color)
        return snackbar
    }

    fun createErrorSnackbar(parentView: View, text: String): Snackbar {
        return createSnackbar(parentView, text, Snackbar.LENGTH_LONG, ERROR_COLOR)
    }

    fun createNotificationSnackbar(parentView: View, text: String): Snackbar {
        return createSnackbar(parentView, text, Snackbar.LENGTH_LONG, NORMAL_COLOR)
    }

    fun createSuccessSnackbar(parentView: View, text: String): Snackbar {
        return createSnackbar(parentView, text, Snackbar.LENGTH_LONG, SUCCESS_COLOR)
    }
}
