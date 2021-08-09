package com.aqua30.otpdemo.screens.login.sendotp

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.aqua30.otpdemo.data.DELAY_HOME
import com.aqua30.otpdemo.data.DELAY_PHONE_PROMPT
import com.aqua30.otpdemo.data.getCountryCode
import com.aqua30.otpdemo.data.getPhoneHintIntent
import com.aqua30.otpdemo.koltin.databinding.FragSendOtpBinding
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import me.ibrahimsn.lib.PhoneNumberKit


@AndroidEntryPoint
class SendOtpFragment: Fragment() {

    lateinit var editPhone: TextInputEditText
    lateinit var viewBinding: FragSendOtpBinding
    private val viewModel: SendOtpViewModel by activityViewModels()
    private lateinit var promptLauncher: ActivityResultLauncher<IntentSenderRequest>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragSendOtpBinding.inflate(inflater,container,false)
        viewBinding.viewModel = viewModel
        editPhone = viewBinding.editPhone
        formatPhoneField()
        bindViews()
        registerPhonePrompt()
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = getPhoneHintIntent(activity as FragmentActivity)
            promptLauncher.launch(IntentSenderRequest.Builder(intent).build())
        }, DELAY_PHONE_PROMPT)
    }

    private fun bindViews() {
        editPhone.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    viewModel.mPhoneNumber = it.toString()
                }
            }
        })
    }

    private fun registerPhonePrompt() {
        try {
            promptLauncher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val credential: Credential? = it.data?.getParcelableExtra(Credential.EXTRA_KEY)
                    viewBinding.editPhone.setText(credential?.id)   /* the selected phone number from hint prompt */
                }
            }
        } catch (e: IntentSender.SendIntentException) {
            e.printStackTrace()
        }
    }

    private fun formatPhoneField() {
        val phoneNumberKit = PhoneNumberKit(activity as FragmentActivity)
        phoneNumberKit.attachToInput(viewBinding.containerPhone, viewModel.mCountryCode)
    }

    companion object Companion {
        fun instance(): SendOtpFragment {
            return SendOtpFragment()
        }
    }
}