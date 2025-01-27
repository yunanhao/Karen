package com.project.base.ui.viewadapter.edittext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.project.base.ui.viewadapter.BindingConsumer

/**
 * EditText输入文字改变的监听
 */
@BindingAdapter(value = ["textChanged"], requireAll = false)
fun addTextChangedListener(
    editText: EditText,
    textChanged: BindingConsumer<String>?
) {
    editText.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(
            s: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
            textChanged?.call(s.toString())
        }

        override fun afterTextChanged(editable: Editable) {}
    })
}