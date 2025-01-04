package com.jabozaroid.abopay.core.common.infotype.di

import com.jabozaroid.abopay.core.common.infotype.enums.InfoType
import dagger.MapKey

/**
 * Created on 13,August,2024
 */

@MapKey
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class InfoKey(val value: com.jabozaroid.abopay.core.common.infotype.enums.InfoType) {
}