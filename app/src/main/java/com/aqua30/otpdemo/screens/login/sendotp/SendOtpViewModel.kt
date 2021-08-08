package com.aqua30.otpdemo.screens.login.sendotp

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aqua30.otpdemo.data.EVENT_SEND_OTP
import com.aqua30.otpdemo.data.getCountryCode
import com.aqua30.otpdemo.data.getCountryISO
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SendOtpViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val otpUseCase: OtpUseCase): ViewModel() {

    /* observable fields */
    val mPhoneEnabled = ObservableBoolean(true)
    val mLoading = ObservableBoolean(false)
    val mError = ObservableBoolean(false)

    /* variables required for otp sending process */
    var mCountryCode: Int = getCountryCode(context)
    var mCountryISO: String = getCountryISO(context)
    var mPhoneNumber: String = ""

    /* live data for sending events back to view */
    val otpEvents: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }

    fun onSendOtpClick() {
        mError.set(false)
        otpUseCase.setPhoneNumber(mPhoneNumber, mCountryISO)
        val isNumberValid = otpUseCase.isNumberValid()
        mError.set(!isNumberValid)
        mPhoneEnabled.set(!isNumberValid)
        mLoading.set(isNumberValid)
        if (isNumberValid) otpEvents.value = EVENT_SEND_OTP
    }

    fun otpSendStatus(isSent: Boolean) {
        mLoading.set(!isSent)
        mPhoneEnabled.set(isSent)
    }

    fun phoneNumber(): String {
        return otpUseCase.formattedNumber()
    }
}