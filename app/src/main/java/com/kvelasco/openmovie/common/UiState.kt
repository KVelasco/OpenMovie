package com.kvelasco.openmovie.common

data class UiState<T>(
    val progressVisibility: Int,
    val error: Throwable?,
    val result: T?
)