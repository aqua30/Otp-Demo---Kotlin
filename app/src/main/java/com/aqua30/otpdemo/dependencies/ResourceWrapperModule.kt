package com.aqua30.otpdemo.dependencies

import com.aqua30.otpdemo.data.impl.prefs.IPref
import com.aqua30.otpdemo.data.impl.prefs.PrefImpl
import com.aqua30.otpdemo.data.impl.resrc.IRes
import com.aqua30.otpdemo.data.impl.resrc.ResImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ResourceWrapperModule {

    @Singleton
    @Binds
    abstract fun bindResImpl(resImpl: ResImpl): IRes

    @Singleton
    @Binds
    abstract fun bindPrefImpl(prefImpl: PrefImpl): IPref
}