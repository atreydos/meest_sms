package io.atreydos.meestsms.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.security.ProviderInstaller
import io.atreydos.meestsms.Constant.SERVER_URL
import io.atreydos.meestsms.R
import io.atreydos.meestsms.view.common.BaseActivity
import io.atreydos.meestsms.view.common.annotation.Layout
import io.atreydos.meestsms.view.messaging.MessagingActivity
import org.koin.android.ext.android.getKoin

@Layout(R.layout.activity_zigote)
class ZigoteActivity : BaseActivity(), ProviderInstaller.ProviderInstallListener {

    companion object {
        private const val SERVER_AUTHORITY = "apii.meest-group.com"
        private const val ERROR_DIALOG_REQUEST_CODE = 1
    }

    private var retryProviderInstall: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ProviderInstaller.installIfNeededAsync(applicationContext, this)
    }

    override fun onProviderInstallFailed(errorCode: Int, recoveryIntent: Intent) {
        GoogleApiAvailability.getInstance().apply {
            if (isUserResolvableError(errorCode))
                showErrorDialogFragment(this@ZigoteActivity, errorCode, ERROR_DIALOG_REQUEST_CODE) {
                    onProviderInstallerNotAvailable()
                }
            else
                onProviderInstallerNotAvailable()
        }
    }

    override fun onProviderInstalled() {
        val securedServerUrl = Uri.Builder().scheme("https").authority(SERVER_AUTHORITY).build().toString()
        getKoin().setProperty(SERVER_URL, securedServerUrl)
        navigateToMessagingActivity()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ERROR_DIALOG_REQUEST_CODE)
            retryProviderInstall = true
    }

    /**
     * On resume, check to see if we flagged that we need to reinstall the provider.
     */
    override fun onPostResume() {
        super.onPostResume()
        if (retryProviderInstall) {
            // We can now safely retry installation.
            ProviderInstaller.installIfNeededAsync(this, this)
        }
        retryProviderInstall = false
    }

    private fun onProviderInstallerNotAvailable() {
        val unsecuredServerUrl = Uri.Builder().scheme("http").authority(SERVER_AUTHORITY).build().toString()
        getKoin().setProperty(SERVER_URL, unsecuredServerUrl)
        navigateToMessagingActivity()
    }

    private fun navigateToMessagingActivity() {
        startActivity(Intent(this, MessagingActivity::class.java))
        finish()
    }
}
