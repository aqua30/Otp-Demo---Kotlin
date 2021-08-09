package com.aqua30.otpdemo.data.otp

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.aqua30.otpdemo.data.*
import com.aqua30.otpdemo.data.EVENT_SEND_FAILED
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class OtpManagerImpl @Inject constructor(private val activity: FragmentActivity): IOtpManager {

    private lateinit var mAuth: FirebaseAuth
    lateinit var mResendToken: PhoneAuthProvider.ForceResendingToken
    var mVerificationId: String = ""

    private val events: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }

    override fun initFirebase() {
        mAuth = FirebaseAuth.getInstance()
    }

    override fun sendOtp(mobile: String, resend: Boolean) {
        if (resend) resend(mobile)
        else send(mobile)
    }

    override fun verifyOtp(otp: String) {
        val credential = PhoneAuthProvider.getCredential(
            mVerificationId,
            otp
        )
        mAuth.signInWithCredential(credential).addOnCompleteListener(activity) {
            events.value = if (it.isSuccessful) EVENT_VALIDATE_SUCCESS else EVENT_VALIDATE_FAILED
        }
    }

    override fun subscribe(): MutableLiveData<Int> = events

    private fun send(mobile: String) {
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(mobile)
            .setTimeout(5L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(mCallbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun resend(mobile: String) {
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(mobile)
            .setTimeout(5L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(mCallbacks)
            .setForceResendingToken(mResendToken)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(authCredential: PhoneAuthCredential) {}

        override fun onVerificationFailed(e: FirebaseException) {
            events.value = EVENT_SEND_FAILED
        }

        override fun onCodeSent(verificationId: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(verificationId, forceResendingToken)
            mResendToken = forceResendingToken
            mVerificationId = verificationId
            events.value = EVENT_SEND_SUCCESS
        }

    }
}