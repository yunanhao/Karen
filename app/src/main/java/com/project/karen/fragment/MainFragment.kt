package com.project.karen.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.project.app.databinding.FragmentMainBinding
import com.project.base.ui.BaseFragment
import com.project.karen.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {
    override val mBinding: FragmentMainBinding by lazy {
        FragmentMainBinding.inflate(layoutInflater)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.tv.text = mViewModel?.toString()
        lifecycleScope.launch {
            mViewModel?.data?.collectLatest {
                mBinding.tv.text = it
            }
        }
    }
}