package com.aqua30.otpdemo.dependencies

import com.aqua30.otpdemo.screens.navigator.Navigator
import com.aqua30.otpdemo.screens.navigator.NavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class NavigatorModule {

    @Binds
    abstract fun bindNavigatorImpl(navigatorImpl: NavigatorImpl) : Navigator
}