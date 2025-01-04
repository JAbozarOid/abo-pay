package com.jabozaroid.abopay.core.analytics.di

import com.jabozaroid.abopay.core.analytics.tracker.MetricaTracker
import com.jabozaroid.abopay.core.analytics.tracker.MetrixTracker
import com.jabozaroid.abopay.core.analytics.tracker.Tracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

/**
 * Created on 11,August,2024
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class TrackerModule {

    @Binds
    @IntoSet
    abstract fun bindsMetricaTracker(tracker: MetricaTracker): Tracker


    @Binds
    @IntoSet
    abstract fun bindsMetrixTracker(tracker: MetrixTracker): Tracker

}
