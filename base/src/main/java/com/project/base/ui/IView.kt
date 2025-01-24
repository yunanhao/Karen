package com.project.base.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.project.base.event.LiveDataBus

/**
 * V 层，这里的视图都是 Activity 或 Fragment
 */
interface IView<V : ViewBinding, M : ViewModel?>: IArgumentsFromBundle {
    /**
     * 1.
     * 初始化外部传进来的参数
     */
    fun initParam() {}

    /**
     * 初始化标题等
     */
    fun initToolbar(){}

    /**
     * 2.
     * 初始化界面观察者
     */
    fun initViewObservable() {}

    /**
     * 3.
     * 初始化数据
     */
    fun initData() {}

    /**
     * 监听点击、滑动事件
     */
    fun initListener(){}

    /**
     * 初始化 DataBinding，基类应该在初始化后设为 final
     */
    fun initBinding(inflater: LayoutInflater, container: ViewGroup?): V

    /**
     * 初始化视图和 VM
     */
    fun initViewModel()

    /**
     * 初始化通用的 UI 改变事件，基类应该在初始化后设为 final
     */
    fun initUiChangeLiveData()

    /**
     * 移除事件总线监听，避免内存泄露
     */
    fun removeLiveDataBus(owner: LifecycleOwner) {
        LiveDataBus.removeObserve(owner)
        LiveDataBus.removeStickyObserver(this)
    }

}