package com.dicoding.picodiploma.capstoneartion.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class NameEditText : AppCompatEditText {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //do nothing
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val name = s.toString()
                when {
                    !name.isValidName() -> error = "Name is incorrect"
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                //do nothing
            }

        })
    }
    private fun CharSequence.isValidName() = !isNullOrEmpty() && !this.contains("[0-9]".toRegex())
}
