package com.aqua30.otpdemo.screens.login.sendotp

import android.util.Log
import com.google.firebase.auth.PhoneAuthProvider
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import javax.inject.Inject

class OtpUseCase @Inject constructor() {

    private val mPhoneUtil = PhoneNumberUtil.getInstance()
    private var mPhoneProto: Phonenumber.PhoneNumber? = null

    fun setPhoneNumber(mPhoneNumber: String, mCountryISO: String) {
        try {
            mPhoneProto = mPhoneUtil.parse(mPhoneNumber,mCountryISO)
        } catch (e: NumberParseException) {
            Log.e("exception",e.message + "")
        }
    }

    fun isNumberValid(): Boolean {
        return mPhoneProto != null && mPhoneUtil.isValidNumber(mPhoneProto)
    }

    fun formattedNumber(): String {
        return mPhoneProto?.let {
            mPhoneUtil.format(it, PhoneNumberUtil.PhoneNumberFormat.E164)
        }.toString()
    }
}