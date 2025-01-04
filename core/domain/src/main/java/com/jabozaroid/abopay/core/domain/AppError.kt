package com.jabozaroid.abopay.core.domain

sealed interface AppError {
    data object NetworkError : AppError
    data object ServerError : AppError
    /***Add more specific error types as needed */
}