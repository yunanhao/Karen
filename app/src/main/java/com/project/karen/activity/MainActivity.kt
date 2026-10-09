package com.project.karen.activity

import android.os.Bundle
import android.widget.SeekBar
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.project.app.databinding.ActivityMainBinding
import com.project.base.ui.BaseActivity
import com.project.base.util.DrawableUtil
import com.project.base.util.common.ToastUtils
import com.project.karen.fragment.MainFragment
import com.project.karen.fragment.ImagePreviewDialogFragment
import com.project.karen.fragment.CommonDialogFragment
import com.project.karen.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import java.util.Calendar

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
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val calendar = Calendar.getInstance()
                while (true) {
                    calendar.timeInMillis = System.currentTimeMillis()
                    mViewModel?.data?.emit("${calendar.time}\n${mBinding.seek.progress}")
                    delay(1000)
                }
            }
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(mBinding.fragmentContainerPanel.id, MainFragment())
                .commit()
        }

        observe(mViewModel?.getContentUri) {
//            mBinding.iv.setImageURI(it)
            it?.let { uri -> ImagePreviewDialogFragment.newInstance(uri).show(supportFragmentManager) }
        }

        Snackbar.make(mBinding.root, "Hello", Snackbar.LENGTH_INDEFINITE)
//            .show()
//        mBinding.loading.show()
        DrawableUtil.createColor().color
    }

    fun showDialog() {
        ToastUtils.showLong(mViewModel?.toString())

        CommonDialogFragment().show(supportFragmentManager)
    }
}
