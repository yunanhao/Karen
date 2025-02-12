package com.project.karen.activity

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.project.app.databinding.ActivityMainBinding
import com.project.base.ui.BaseActivity
import com.project.base.ui.BaseDialogFragment
import com.project.base.ui.widget.ZoomImageView
import com.project.base.util.DrawableUtil
import com.project.base.util.common.ToastUtils
import com.project.karen.fragment.MainFragment
import com.project.karen.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import java.util.Calendar
import java.util.Date

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val mBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var count = 0
        mBinding.floatingActionButton.setOnClickListener {
//            getContent.launch("image/*")

        }
        mBinding.seek.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                lifecycleScope.launch {
                    mViewModel?.count?.emit(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        lifecycleScope.launch {
            val calendar = Calendar.getInstance()
            while (true) {
                calendar.timeInMillis = System.currentTimeMillis()
                mViewModel?.data?.emit("${calendar.time}\n${mBinding.seek.progress}")
                delay(java.time.Duration.ofMillis(1000))
            }
        }

        supportFragmentManager.beginTransaction()
            .add(mBinding.fragmentContainerPanel.id, MainFragment())
            .commit()

        observe(mViewModel?.getContentUri) {
//            mBinding.iv.setImageURI(it)
            val view = ZoomImageView(this)
            view.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            view.setBackgroundColor(0xffff0000.toInt())
            view.scaleType = ImageView.ScaleType.FIT_XY

            BaseDialogFragment(layoutView = view) { vh ->
                (vh.root as? ImageView)?.setImageURI(it)
            }
//                .setTheme(R.style.base_NiceDialogStyle)
                .setDimAmount(1f).setHeight(-1).show(supportFragmentManager)
        }

        Snackbar.make(mBinding.root, "Hello", Snackbar.LENGTH_INDEFINITE)
//            .show()
//        mBinding.loading.show()
        DrawableUtil.createColor().color
    }

    fun showDialog() {
        ToastUtils.showLong(mViewModel?.toString())

        BaseDialogFragment(com.project.base.R.layout.base_dialog_common) { vh ->
            vh.findView<TextView>(com.project.base.R.id.tv_title)?.text = "警告"
            vh.setOnClickListener(com.project.base.R.id.btn_left) {
                vh.close()
            }
            vh.setOnClickListener(com.project.base.R.id.btn_right) {
                vh.close()
            }
            vh.setOnClickListener(com.project.base.R.id.top_button) {
                vh.close()
            }
            vh.setOnClickListener(com.project.base.R.id.bottom_button) {
                vh.close()
            }
            vh.root.setOnClickListener {
                vh.close()
            }
        }.show(supportFragmentManager)
    }
}