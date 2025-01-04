package com.jabozaroid.abopay.core.network.di.annotation

import com.jabozaroid.abopay.core.network.helper.InfoType
import dagger.MapKey

@MapKey
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class InfoKey(val value: InfoType) {
}
