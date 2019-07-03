package io.atreydos.meestsms.view.widget

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import io.atreydos.meestsms.view.common.extension.formatAsPhoneNumber
import kotlinx.android.synthetic.main.activity_main.*

class PhoneEditText : TextInputEditText {

    private var isErasing: Boolean = false
    var textLength = 0

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}


    override fun onTextChanged(charSequence: CharSequence, start: Int, lengthBefore: Int, lengthAfter: Int) {
        isErasing = lengthAfter < lengthBefore
        text.toString().let { text ->
            if (text.length != textLength || text.length < 6) {
                textLength = charSequence.length
                setText(text.formatAsPhoneNumber())
            }
        }
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
    }


    public override fun onSelectionChanged(start: Int, end: Int) {
        if (text.isNullOrEmpty())
            return

        val text = text.toString()
        val targetSelectionPosition = text.indexOf('_')
        val selectionPosition =
            if (targetSelectionPosition > -1)
                if (text[targetSelectionPosition - 1] == ' ')
                    if (text[targetSelectionPosition - 2] == ')') targetSelectionPosition - 2
                    else targetSelectionPosition - 1
                else targetSelectionPosition
            else text.length
        setSelection(selectionPosition)
        super.onSelectionChanged(start, end)
    }
}
