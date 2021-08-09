package com.aqua30.otpdemo.data

const val KEY_USER_LOGIN = "is_user_logged_in"
const val DELAY_HOME = 1000L
const val DELAY_LOGIN = 2000L
const val DELAY_PHONE_PROMPT = 600L

/* events used by view models */
const val EVENT_SEND_OTP = 1
const val EVENT_VALIDATE_OTP = 2
const val EVENT_RESEND_OTP = 3
const val EVENT_CHANGE_NUMBER = 4

/* events used by otp manager */
const val EVENT_SEND_SUCCESS = 11
const val EVENT_SEND_FAILED = 12
const val EVENT_VALIDATE_SUCCESS = 13
const val EVENT_VALIDATE_FAILED = 14