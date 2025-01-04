package com.jabozaroid.abopay.feature.webview.model

/**
 *  Created on 8/10/2024 
 **/
enum class HttpMethodType {
    None,
    Get,
    Post,
    AuthorizedGet,
    AuthorizedPost;

    companion object{
        fun fromString(method: String): HttpMethodType? {
            return try {
                valueOf(method)
            } catch (e: Exception) {
                None
            }
        }
    }

}