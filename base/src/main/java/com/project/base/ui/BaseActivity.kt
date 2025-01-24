package com.project.base.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel<*>> :
    AppCompatActivity(), View.OnClickListener, IArgumentsFromIntent {

    abstract val binding: VB

    var viewModel: VM? = null

    val startActivityForResult by lazy {
        // ActivityResultContracts.StartActivityForResult()	启动一个 Activity，并返回结果
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            viewModel?.startActivityResult?.value = it
        }
    }

    val getContent by lazy {
        // ActivityResultContracts.GetContent()	打开文件选择器并返回所选文件的 Uri
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            viewModel?.getContentUri?.value = it
        }
    }

    val requestMultiplePermissions by lazy {
        // ActivityResultContracts.RequestMultiplePermissions() 请求多个权限
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            viewModel?.queryMultiplePermissions?.value = it
        }
    }

    val requestPermission by lazy {
        // ActivityResultContracts.RequestPermission() 请求单个权限
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }
    }

    val openDocument by lazy {
        // ActivityResultContracts.OpenDocument() 打开文件选择器，支持选择多个文件，返回所选文件的 Uri
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { }
    }

    val takePicture by lazy {
        // ActivityResultContracts.TakePicture() 启动相机拍摄照片，并将结果保存到指定的 Uri 中
        registerForActivityResult(ActivityResultContracts.TakePicture()) { }
    }

    val takePicturePreview by lazy {
        // ActivityResultContracts.TakePicturePreview()	启动相机拍摄照片，返回缩略图
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { }
    }

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
            ViewModelProvider(this)[modelClass]
        } else {
            // 如果无法推断出 ViewModel 类型，抛出异常或返回 null
            null
        }
    }

    fun <T> observe(liveData: LiveData<T>, onChanged: ((t: T) -> Unit)) =
        liveData.observe(this) {
            try {
                onChanged(it)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v("${componentName.className} ${hashCode()}", "onCreate")
        super.onCreate(savedInstanceState)

        // 让 LiveData 和 xml 可以双向绑定
        (binding as? ViewDataBinding)?.lifecycleOwner = this
        setContentView(binding.root)
        viewModel = createViewModel()
        intent
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