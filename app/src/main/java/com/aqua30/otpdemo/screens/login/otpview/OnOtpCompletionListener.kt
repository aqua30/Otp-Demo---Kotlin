package com.aqua30.otpdemo.screens.login.otpview

interface OnOtpCompletionListener {
    fun onOtpCompleted(otp: String)
    fun onOtpIncomplete()
}