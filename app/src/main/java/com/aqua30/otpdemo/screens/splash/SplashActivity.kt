package com.aqua30.otpdemo.screens.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.viewbinding.ViewBinding
import com.aqua30.otpdemo.base.BaseActivity
import com.aqua30.otpdemo.data.impl.prefs.IPref
import com.aqua30.otpdemo.data.DELAY_HOME
import com.aqua30.otpdemo.data.DELAY_LOGIN
import com.aqua30.otpdemo.data.KEY_USER_LOGIN
import com.aqua30.otpdemo.koltin.R
import com.aqua30.otpdemo.koltin.databinding.SplashBinding
import com.aqua30.otpdemo.screens.navigator.Navigator
import com.aqua30.otpdemo.screens.navigator.Screen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    lateinit var logoImage: ImageView

    @Inject lateinit var navigor: Navigator
    @Inject lateinit var pref: IPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logoImage = (viewBinding as SplashBinding).imageLogo
        logoImage.animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
    }

    override fun onResume() {
        super.onResume()
        logoImage.animate().setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                loadNextScreen(if (pref.bool(KEY_USER_LOGIN)) Screen.HOME else Screen.LOGIN,
                    if (pref.bool(KEY_USER_LOGIN)) DELAY_HOME else DELAY_LOGIN)
            }
        }).start()
    }

    private fun loadNextScreen(screen: Screen, delay: Long) {
        Handler(Looper.getMainLooper()).postDelayed({
            navigor.navigateTo(screen)
        }, delay)
    }

    override fun binding(): ViewBinding {
        return SplashBinding.inflate(layoutInflater)
    }
}