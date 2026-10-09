package com.project.karen.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.Lifecycle
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.project.app.databinding.FragmentMainBinding
import com.project.base.ui.BaseFragment
import com.project.karen.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {
    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMainBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.kiComposeView.setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
        )
        mBinding.kiComposeView.setBackgroundColor(0xAA990066.toInt())

        mBinding.tv.text = mViewModel?.toString()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    mViewModel?.data?.collectLatest { mBinding.tv.text = it }
                }
                launch {
                    mViewModel?.count?.collectLatest { mBinding.tv.background.level = it }
                }
            }
        }
    }

    @Composable
    fun Greeting(name: String) {
        Text(text = "Hello, $name")
    }
}
