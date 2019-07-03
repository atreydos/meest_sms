package io.atreydos.meestsms

import android.util.Log
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import io.atreydos.meestsms.interactor.helper.MeestSms

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MeestSmsInstrumentedTest {

    @Test
    fun buildSendMessageRequestUri_isCorrect() {
        val requestUrl = MeestSms.buildSendMessageRequestUrl("380631877730", "Test message %2b#\$_+, abcABC їъ")
        val expectedRequestUrl = "/services/sms/add_msg.php?phone=380631877730&text=Test+message+%252b%23%24_%2B%2C+abcABC+%D1%97%D1%8A&user=user_mob&sign=e492e58a4831a12ef552168f824f8237"
        Log.d("AtrL","url returned = [$requestUrl]")
        Log.d("AtrL","url expected = [$expectedRequestUrl]")
        assertEquals(requestUrl, expectedRequestUrl)
    }
}
