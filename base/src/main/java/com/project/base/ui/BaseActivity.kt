package com.project.base.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class BaseActivity<V : ViewBinding, M : ViewModel> :
    AppCompatActivity(), View.OnClickListener {

    abstract val binding: V

    var mViewModel: M? = null

    open fun createViewModel(): M? {
        // 获取父类的泛型类型
        val type: Type? = javaClass.genericSuperclass
        var modelClass: Class<M>? = null

        if (type is ParameterizedType) {
            // 获取泛型的第二个类型参数
            modelClass = type.actualTypeArguments[1] as? Class<M>
        }

        return if (modelClass != null) {
            // 使用 ViewModelProvider 获取 ViewModel
            ViewModelProvider(this)[modelClass]
        } else {
            // 如果无法推断出 ViewModel 类型，抛出异常或返回 null
            null
        }
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v("${componentName.className} ${hashCode()}", "onCreate")
        super.onCreate(savedInstanceState)
        // 让 LiveData 和 xml 可以双向绑定
        (binding as? ViewDataBinding)?.lifecycleOwner = this
        setContentView(binding.root)
    }

    @CallSuper
    override fun onRestart() {
        Log.v("${componentName.className} ${hashCode()}", "onRestart")
        super.onRestart()
    }

    @CallSuper
    override fun onStart() {
        Log.v("${componentName.className} ${hashCode()}", "onStart")
        super.onStart()
    }

    @CallSuper
    override fun onResume() {
        Log.v("${componentName.className} ${hashCode()}", "onResume")
        super.onResume()
    }

    @CallSuper
    override fun onPause() {
        Log.v("${componentName.className} ${hashCode()}", "onPause")
        super.onPause()
    }

    @CallSuper
    override fun onStop() {
        Log.v("${componentName.className} ${hashCode()}", "onStop")
        super.onStop()
    }

    @CallSuper
    override fun onDestroy() {
        Log.v("${componentName.className} ${hashCode()}", "onDestroy")
        super.onDestroy()
        (binding as? ViewDataBinding)?.unbind()
    }

    override fun onClick(v: View) {
    }

}