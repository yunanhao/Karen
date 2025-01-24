package com.project.base.ui

import android.content.Context
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : AppCompatDialogFragment(), IArgumentsFromBundle {

    abstract val binding: VB

    var viewModel: VM? = null

    open fun createViewModel(): VM? {
        // 获取父类的泛型类型
        val type: Type? = javaClass.genericSuperclass
        var modelClass: Class<VM>? = null

        if (type is ParameterizedType) {
            // 获取泛型的第二个类型参数
            val viewModelType = type.actualTypeArguments[1]
            if (viewModelType is Class<*>) {
                modelClass = viewModelType as Class<VM>?
            }else if (viewModelType is ParameterizedType) {
                modelClass = viewModelType.rawType as Class<VM>?
            }
        }

        return if (modelClass != null) {
            // 使用 ViewModelProvider 获取 ViewModel
            ViewModelProvider(this)[modelClass]
        } else {
            // 如果无法推断出 ViewModel 类型，抛出异常或返回 null
            null
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = createViewModel()
    }
}
