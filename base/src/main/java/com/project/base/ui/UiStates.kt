package com.project.base.ui

sealed interface UiState<out T> {

    data class Success<T>(val data: T) : UiState<T>

    data class Error(val e: UiError) : UiState<Nothing>

    data class Loading(val l: UiLoading = UiLoading()) : UiState<Nothing>

    data object Default : UiState<Nothing>

}

class UiLoading {
    private val timestamp: Long = System.currentTimeMillis()
}

class UiError(val code: Int, val message: String?) {
    constructor(message: String?) : this(400, message)

    private val timestamp: Long = System.currentTimeMillis()
}