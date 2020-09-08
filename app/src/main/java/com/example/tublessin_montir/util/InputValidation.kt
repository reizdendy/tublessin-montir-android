package com.example.tublessin_montir.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView


abstract class TextValidator(private val textView: TextView) : TextWatcher {
    abstract fun validate(textView: TextView?, text: String?)
    override fun afterTextChanged(s: Editable) {
        val text = textView.text.toString()
        validate(textView, text)
    }

    override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) { /* Don't care */
    }

    override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) { /* Don't care */
    }

}

fun InputValidation (input: EditText){
    if (input.text.toString().length == 0 ){
        input.setError("")
    }
}