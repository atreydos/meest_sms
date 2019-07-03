package io.atreydos.meestsms.view.common

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import io.atreydos.meestsms.view.common.annotation.Layout


abstract class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cls = javaClass
        if (!cls.isAnnotationPresent(Layout::class.java)) return
        val annotation = cls.getAnnotation(Layout::class.java)
        val layout = annotation as Layout
        setContentView(layout.id)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
