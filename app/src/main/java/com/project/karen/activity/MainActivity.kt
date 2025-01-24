package com.project.karen.activity

import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.project.app.R
import com.project.app.databinding.ActivityMainBinding
import com.project.base.databinding.BaseDialogCommonBinding
import com.project.base.ui.BaseActivity
import com.project.base.ui.BaseDialogFragment
import com.project.base.util.common.ToastUtils
import com.project.karen.fragment.MainFragment
import com.project.karen.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import kotlinx.coroutines.yield

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val mBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding.floatingActionButton.setOnClickListener {
            ToastUtils.showLong(mViewModel?.toString())
            BaseDialogCommonBinding.inflate(layoutInflater)
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

        lifecycleScope.launch {
            for (i in 0..1000) {
                delay(java.time.Duration.ofMillis(500))
                mViewModel?.data?.emit("2233 -> $i")
            }
        }

        supportFragmentManager.beginTransaction().add(R.id.fragment_container_panel, MainFragment())
            .commit()

    }

}