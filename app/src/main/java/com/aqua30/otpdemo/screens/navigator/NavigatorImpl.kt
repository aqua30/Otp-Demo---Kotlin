package com.aqua30.otpdemo.screens.navigator

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.aqua30.otpdemo.screens.home.HomeActivity
import com.aqua30.otpdemo.screens.login.LoginActivity
import javax.inject.Inject

class NavigatorImpl @Inject constructor(private val activity: FragmentActivity): Navigator {

    override fun navigateTo(screen: Screen) {
        when (screen) {
            Screen.LOGIN -> {
                activity.startActivity(Intent(activity, LoginActivity::class.java))
            }
            Screen.HOME -> {
                activity.startActivity(Intent(activity, HomeActivity::class.java))
            }
        }
    }
}