package io.atreydos.meestsms.view.messaging

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import io.atreydos.meestsms.R
import io.atreydos.meestsms.Scope
import io.atreydos.meestsms.view.common.BaseActivity
import io.atreydos.meestsms.view.common.SnackbarFactory
import io.atreydos.meestsms.view.common.annotation.Layout
import io.atreydos.meestsms.view.common.extension.extractDigits
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

@Layout(R.layout.activity_main)
class MessagingActivity : BaseActivity() {

    val model: MessagingViewModel by getKoin().createScope(
        MessagingActivity::class.java.simpleName,
        named(Scope.MESSAGING.name)
    ).inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model.searchEvent.observe(this, Observer { searchEvent ->
            searchEvent?.let {
                if (searchEvent.isLoading) {
                    showProgress()
                    disableInput()
                } else {
                    hideProgress()
                    enableInput()
                    if (searchEvent.isSuccess)
                        displaySuccess()
                    else if (searchEvent.error != null)
                        displayThrowable(searchEvent.error)

                }
            }
        })
        model.uiData.observe(this, Observer { uiData ->
            uiData?.let { data ->
                etRecipientNumber.setText(data.recipientNumber ?: "")
                etMessage.setText(data.message ?: "")
            }
        })

        btnSend.setOnClickListener {
            val recipientNumber = etRecipientNumber.text.toString().extractDigits()
            val message = etMessage.text.toString()
            model.sendSms(recipientNumber, message)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        getKoin().getScope(MessagingActivity::class.java.simpleName).close()
    }

    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    private fun disableInput() {
        etRecipientNumber.isEnabled = false
        etMessage.isEnabled = false
        btnSend.isEnabled = false
    }

    private fun enableInput() {
        etRecipientNumber.isEnabled = true
        etMessage.isEnabled = true
        btnSend.isEnabled = true
    }

    private fun displaySuccess() {
        SnackbarFactory.createSuccessSnackbar(clRoot, getString(R.string.txt_message_sent_successfully)).show()
    }

    private fun displayThrowable(t: Throwable) {
        SnackbarFactory.createErrorSnackbar(clRoot, t.localizedMessage).show()
        t.printStackTrace()
    }
}
