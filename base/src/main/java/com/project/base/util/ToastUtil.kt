package com.project.base.util

import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.project.BaseApplication

class ToastUtil private constructor() {
    private val mToast: Toast = Toast(BaseApplication.appContext)
    private val mTextView: View

    init {
        val layoutInflater = LayoutInflater.from(BaseApplication.appContext)
        mTextView = layoutInflater.inflate(android.R.layout.activity_list_item, null)
    }

    fun show(obj: Any?) {
        mToast.view = mTextView
    }

    companion object {
        private var mInstance: ToastUtil? = null
        val instance: ToastUtil?
            get() {
                if (mInstance == null) {
                    synchronized(ToastUtil::class.java) {
                        if (mInstance == null) {
                            mInstance = ToastUtil()
                        }
                    }
                }
                return mInstance
            }
    }

}