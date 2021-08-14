package com.aqua30.otpdemo.screens.login

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.aqua30.otpdemo.base.BaseActivity
import com.aqua30.otpdemo.data.*
import com.aqua30.otpdemo.data.impl.prefs.IPref
import com.aqua30.otpdemo.data.impl.resrc.IRes
import com.aqua30.otpdemo.data.otp.IOtpManager
import com.aqua30.otpdemo.koltin.R
import com.aqua30.otpdemo.koltin.databinding.LoginBinding
import com.aqua30.otpdemo.navigator.Navigator
import com.aqua30.otpdemo.navigator.Screen
import com.aqua30.otpdemo.screens.login.sendotp.SendOtpFragment
import com.aqua30.otpdemo.screens.login.sendotp.SendOtpViewModel
import com.aqua30.otpdemo.screens.login.validateotp.ValidateOtpFragment
import com.aqua30.otpdemo.screens.login.validateotp.ValidateOtpViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity: BaseActivity() {

    /* view objects */
    private lateinit var binding: LoginBinding
    private lateinit var bannerHeaderView: TextView
    private lateinit var bannerSubHeaderView: TextView
    private lateinit var bannerImage: ImageView

    /* dependency objects */
    @Inject lateinit var navigor: Navigator
    @Inject lateinit var pref: IPref
    @Inject lateinit var res: IRes
    @Inject lateinit var otpManager: IOtpManager

    /* view models objects */
    private val sendOtpViewModel: SendOtpViewModel by viewModels()
    private val validateOtpViewModel: ValidateOtpViewModel by viewModels()

    /* variable to identifier otp request state */
    private var otpRequestState = EVENT_SEND_OTP

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        otpManager.initFirebase()
        bindViews()
        bindFragment(SendOtpFragment.instance(), SendOtpFragment::class.simpleName)
        bindObservers()
    }

    private fun bindObservers() {
        sendOtpViewModel.otpEvents.observe(this, {
            when(it) {
                EVENT_SEND_OTP -> {
                    otpRequestState = EVENT_SEND_OTP
                    otpManager.sendOtp(sendOtpViewModel.phoneNumber())
                }
            }
        })

        validateOtpViewModel.validationEvents.observe(this, {
            when(it) {
                EVENT_RESEND_OTP -> {
                    otpRequestState = EVENT_RESEND_OTP
                    otpManager.sendOtp(sendOtpViewModel.phoneNumber(),resend = true)
                }
                EVENT_CHANGE_NUMBER -> supportFragmentManager.popBackStack()
                EVENT_VALIDATE_OTP -> otpManager.verifyOtp(validateOtpViewModel.otp())
            }
        })

        otpManager.subscribe().observe(this, {
            when(it) {
                EVENT_SEND_SUCCESS -> {
                    showMessage(String.format(getString(R.string.otp_sent), sendOtpViewModel.phoneNumber()))
                    when(otpRequestState) {
                        EVENT_SEND_OTP -> {
                            sendOtpViewModel.otpUpdateStatus()
                            bindFragment(ValidateOtpFragment.instance(), ValidateOtpFragment::class.simpleName)
                        }
                        EVENT_RESEND_OTP -> validateOtpViewModel.otpSendStatus(true)
                    }
                }
                EVENT_SEND_FAILED -> {
                    when(otpRequestState) {
                        EVENT_SEND_OTP -> sendOtpViewModel.otpUpdateStatus()
                        EVENT_RESEND_OTP -> validateOtpViewModel.otpSendStatus(false)
                    }
                    showMessage(String.format(getString(R.string.otp_not_sent), sendOtpViewModel.phoneNumber()))
                }
                EVENT_VALIDATE_SUCCESS -> {
                    validateOtpViewModel.validateOtpState(true)
                    pref.put(KEY_USER_LOGIN, true)
                    navigor.navigateTo(Screen.HOME, bannerImage, res.str(R.string.image_banner_transition))
                }
                EVENT_VALIDATE_FAILED -> validateOtpViewModel.validateOtpState(false)
            }
        })
    }

    private fun bindFragment(fragment: Fragment, tag: String?) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in,R.anim.fade_out)
            .add(R.id.login_frame, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    private fun bindViews() {
        binding = viewBinding as LoginBinding
        bannerHeaderView = binding.bannerHeader
        bannerSubHeaderView = binding.bannerSubHeader
        bannerImage = binding.bannerImage
    }

    override fun binding(): ViewBinding {
        return LoginBinding.inflate(layoutInflater)
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}