package com.aqua30.otpdemo.dependencies

import com.aqua30.otpdemo.data.otp.IOtpManager
import com.aqua30.otpdemo.data.otp.OtpManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class OtpModule {

    @Binds
    abstract fun bindOtpManagerImpl(otpManagerImpl: OtpManagerImpl): IOtpManager
}