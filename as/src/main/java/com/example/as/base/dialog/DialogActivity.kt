package com.karen.base.dialog

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.karen.base.R

class DialogActivity : Activity(), View.OnClickListener {
    private lateinit var globalView: View
    private var firstHeight = 0
    private var isFirst = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_dialog)
        window.decorView.setBackgroundColor(-0xffff67)
        val frame = Rect()
        window.decorView.getWindowVisibleDisplayFrame(frame)
        val statusBarHeight = frame.top
        val contentTop = window.decorView.findViewById<View>(android.R.id.content).top
        //statusBarHeight是上面所求的状态栏的高度
        val titleBarHeight = contentTop - statusBarHeight
        globalView = findViewById(android.R.id.content)
        globalView.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            globalView.getWindowVisibleDisplayFrame(rect)
            if (isFirst) {
                isFirst = false
                firstHeight = rect.height()
            } else {
                val height = rect.height()
                if (height < firstHeight) {
                    ARouter.logger.info("DialogActivity", "键盘打开 " + (firstHeight - height))
                } else {
                    ARouter.logger.info("DialogActivity", "键盘关闭 ")
                }
            }
        }
    }

    override fun onClick(v: View) {
        startActivity(Intent(this, DialogActivity::class.java))
    }
}