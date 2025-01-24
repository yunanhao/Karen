package com.project.base.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : AppCompatDialogFragment(),
    IArgumentsFromBundle {

    abstract val mBinding: VB

    var mViewModel: VM? = null

    open fun createViewModel(): VM? {
        // 获取父类的泛型类型
        val type: Type? = javaClass.genericSuperclass
        var modelClass: Class<VM>? = null

        if (type is ParameterizedType) {
            // 获取泛型的第二个类型参数
            val viewModelType = type.actualTypeArguments[1]
            if (viewModelType is Class<*>) {
                modelClass = viewModelType as Class<VM>?
            } else if (viewModelType is ParameterizedType) {
                modelClass = viewModelType.rawType as Class<VM>?
            }
        }

        return if (modelClass != null) {
            // 使用 ViewModelProvider 获取 ViewModel
            ViewModelProvider(activity ?: this)[modelClass]
        } else {
            // 如果无法推断出 ViewModel 类型，抛出异常或返回 null
            null
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mViewModel = createViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化变量或获取传递的参数
        val args = arguments?.getString("key")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 加载布局
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 视图初始化逻辑
    }

    override fun onResume() {
        super.onResume()
        // 恢复某些状态
    }

    override fun onStart() {
        super.onStart()
        // 开始一些逻辑
    }

    override fun onPause() {
        super.onPause()
        // 暂停相关任务
    }

    override fun onStop() {
        super.onStop()
        // 停止相关逻辑
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 清理视图绑定
    }

    override fun onDestroy() {
        super.onDestroy()
        // 释放资源
    }

    override fun onDetach() {
        super.onDetach()
        // 解除与 Activity 的引用
    }

}
