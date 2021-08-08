package com.aqua30.otpdemo.dependencies

import com.aqua30.otpdemo.screens.login.sendotp.OtpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideOtpUseCase() : OtpUseCase {
        return OtpUseCase()
    }

    /*@Provides
    fun provideValidateOtpUseCase() : ValidateOtpUseCase {
        return ValidateOtpUseCase()
    }*/
}