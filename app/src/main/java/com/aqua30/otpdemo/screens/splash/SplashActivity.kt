package com.aqua30.otpdemo.screens.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.viewbinding.ViewBinding
import com.aqua30.otpdemo.base.BaseActivity
import com.aqua30.otpdemo.data.impl.prefs.IPref
import com.aqua30.otpdemo.data.DELAY_HOME
import com.aqua30.otpdemo.data.DELAY_LOGIN
import com.aqua30.otpdemo.data.KEY_USER_LOGIN
import com.aqua30.otpdemo.data.impl.resrc.IRes
import com.aqua30.otpdemo.koltin.R
import com.aqua30.otpdemo.koltin.databinding.SplashBinding
import com.aqua30.otpdemo.navigator.Navigator
import com.aqua30.otpdemo.navigator.Screen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    /* view objects */
    lateinit var logoImage: ImageView
    lateinit var developerView: TextView

    /* dependency objects */
    @Inject lateinit var navigor: Navigator
    @Inject lateinit var pref: IPref
    @Inject lateinit var res: IRes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logoImage = (viewBinding as SplashBinding).imageLogo
        logoImage.animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        developerView = (viewBinding as SplashBinding).viewDevelopedBy
        developerView.text = HtmlCompat.fromHtml(res.str(R.string.developer),HtmlCompat.FROM_HTML_MODE_LEGACY)
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
        /* additional delay to show the splash */
        Handler(Looper.getMainLooper()).postDelayed({
            navigor.navigateTo(screen, logoImage, res.str(R.string.image_logo_transition))
        }, delay)
    }

    override fun binding(): ViewBinding {
        return SplashBinding.inflate(layoutInflater)
    }
}