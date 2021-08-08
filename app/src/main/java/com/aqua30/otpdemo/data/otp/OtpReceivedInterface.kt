package com.aqua30.otpdemo.data.otp

interface OtpReceivedInterface {
    fun onOtpReceived(otp: String)
    fun onOtpTimeout()
}