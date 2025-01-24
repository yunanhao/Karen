package com.project.base.ui

import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

open class BaseViewModel<T> : ViewModel() {
    //如果界面存在一个数据体控制所有UI 可以使用这个
    val uiState = MutableLiveData<UiState<T>>(UiState.Default)

    //开启activity活动后获取返回值
    val startActivityResult = MutableLiveData<ActivityResult>()

    //获取资源
    val getContentUri = MutableLiveData<Uri?>()

    // 权限申请
    val queryMultiplePermissions = MutableLiveData<Map<String, Boolean>>()

    //数据交流
    val data = MutableSharedFlow<String>(1)

    val load = MutableStateFlow("")
}