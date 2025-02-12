package com.project.karen.viewmodel

import com.project.base.ui.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow

class MainViewModel : BaseViewModel<Nothing>() {
    val count = MutableSharedFlow<Int>(1)
}