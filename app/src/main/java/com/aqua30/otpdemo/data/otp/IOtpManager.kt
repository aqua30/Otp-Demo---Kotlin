package com.aqua30.otpdemo.data.otp

import androidx.lifecycle.MutableLiveData

interface IOtpManager {

    fun initFirebase()
    fun sendOtp(mobile: String, resend: Boolean = false)
    fun verifyOtp(otp: String)
    fun subscribe() : MutableLiveData<Int>
}