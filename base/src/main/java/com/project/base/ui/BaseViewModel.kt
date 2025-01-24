package com.project.base.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

open class BaseViewModel<T> : ViewModel() {
    //如果界面存在一个数据体控制所有UI 可以使用这个
    val uiState = MutableLiveData<UiState<T>>(UiState.Default)

    //数据交流
    val data = MutableSharedFlow<String>(1)

    val load = MutableStateFlow("")
}