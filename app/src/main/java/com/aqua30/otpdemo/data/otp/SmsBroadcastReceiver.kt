package com.aqua30.otpdemo.data.otp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

class SmsBroadcastReceiver : BroadcastReceiver() {

    var otpReceiveInterface: OtpReceivedInterface? = null

    override fun onReceive(context: Context, intent: Intent) {
        Log.e(TAG, "onReceive: ")
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            intent?.extras?.let {
                val mStatus = it.get(SmsRetriever.EXTRA_STATUS) as Status
                when (mStatus.statusCode) {
                    CommonStatusCodes.SUCCESS -> {
                        val message = it[SmsRetriever.EXTRA_SMS_MESSAGE] as String
                        Log.d(TAG, "onReceive: failure $message")
                        val otp = message.substring(0, 6)
                        otpReceiveInterface?.onOtpReceived(otp)
                    }
                    CommonStatusCodes.TIMEOUT -> {
                        Log.e(TAG, "onReceive: failure")
                        otpReceiveInterface?.onOtpTimeout()
                    }
                    else -> {}
                }
            }
        }
    }

    companion object {
        private val TAG = SmsBroadcastReceiver::class.java.name
    }
}