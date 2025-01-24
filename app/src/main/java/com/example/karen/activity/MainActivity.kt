package com.example.karen.activity

import android.os.Bundle
import com.project.app.databinding.ActivityMainBinding
import com.project.base.ui.BaseActivity
import com.project.base.ui.BaseDialogFragment
import com.project.base.ui.BaseEmptyViewModel
import com.project.base.util.common.ToastUtils

class MainActivity : BaseActivity<ActivityMainBinding, BaseEmptyViewModel>() {

    override val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.floatingActionButton.setOnClickListener {
            ToastUtils.showLong(viewModel?.toString())
            BaseDialogFragment(com.project.base.R.layout.base_dialog_common) {

            }.show(supportFragmentManager)
        }
    }

}