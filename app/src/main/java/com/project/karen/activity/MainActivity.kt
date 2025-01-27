package com.project.karen.activity

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.project.app.databinding.ActivityMainBinding
import com.project.base.databinding.BaseDialogCommonBinding
import com.project.base.ui.BaseActivity
import com.project.base.ui.BaseDialogFragment
import com.project.base.ui.widget.ZoomImageView
import com.project.base.util.common.ToastUtils
import com.project.karen.fragment.MainFragment
import com.project.karen.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val mBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding.floatingActionButton.setOnClickListener {
            getContent.launch("image/*")
        }

        lifecycleScope.launch {
            for (i in 0..1000) {
                delay(java.time.Duration.ofMillis(500))
                mViewModel?.data?.emit("2233 -> $i")
            }
        }

        supportFragmentManager.beginTransaction()
            .add(mBinding.fragmentContainerPanel.id, MainFragment())
            .commit()

        observe(mViewModel?.getContentUri) {
            mBinding.iv.setImageURI(it)
            val view = ZoomImageView(this)
            view.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            view.scaleType = ImageView.ScaleType.CENTER
            BaseDialogFragment(layoutView = view) { vh ->
                (vh.root as? ImageView)?.setImageURI(it)
            }.show(supportFragmentManager)
        }
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