package com.aqua30.otpdemo.screens.login.validateotp

import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.aqua30.otpdemo.data.EVENT_RESEND_OTP
import com.aqua30.otpdemo.data.otp.OtpReceivedInterface
import com.aqua30.otpdemo.data.otp.SmsBroadcastReceiver
import com.aqua30.otpdemo.koltin.databinding.FragValidateOtpBinding
import com.aqua30.otpdemo.screens.login.LoginActivity
import com.aqua30.otpdemo.screens.login.otpview.OtpView
import com.google.android.gms.auth.api.phone.SmsRetriever
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ValidateOtpFragment: Fragment(), OtpReceivedInterface {

    private lateinit var otpView: OtpView
    private lateinit var viewBinding: FragValidateOtpBinding
    private val smsReceiver = SmsBroadcastReceiver()
    private val viewModel: ValidateOtpViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startSmsListener()
        bindSmsReceiver()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragValidateOtpBinding.inflate(inflater, container, false)
        viewBinding.viewModel = viewModel
        bindViews()
        bindObserver()
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.startTimer()
    }

    private fun bindObserver() {
        viewModel.validationEvents.observe(activity as LoginActivity, {
            when(it) {
                EVENT_RESEND_OTP -> otpView.setText("")
            }
        })
    }

    private fun bindSmsReceiver() {
        smsReceiver.otpReceiveInterface = this
        val intentFilter = IntentFilter()
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
        activity?.registerReceiver(smsReceiver, intentFilter)
    }

    private fun startSmsListener() {
        val mClient = SmsRetriever.getClient(activity as FragmentActivity)
        val mTask = mClient.startSmsRetriever()
        mTask.addOnSuccessListener { Log.e("sms listener", "started") }
        mTask.addOnFailureListener { Log.e("sms listener", "failed") }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.unregisterReceiver(smsReceiver)
    }

    private fun bindViews() {
        otpView = viewBinding.containerOtp
        otpView.setOtpCompletionListener(viewModel.otpListener)
    }

    companion object Companion {
        fun instance(): ValidateOtpFragment {
            return ValidateOtpFragment()
        }
    }

    override fun onOtpReceived(otp: String) {
        otpView.setText(otp)
        viewModel.onValidateOTPClick()
    }

    override fun onOtpTimeout() {}
}