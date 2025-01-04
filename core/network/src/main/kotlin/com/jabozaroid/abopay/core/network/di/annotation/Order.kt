package com.jabozaroid.abopay.core.network.di.annotation

import dagger.MapKey

/**
Created by aHosseini on 8/31/2022
 */
@MapKey
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Order(val value: Int) {

}