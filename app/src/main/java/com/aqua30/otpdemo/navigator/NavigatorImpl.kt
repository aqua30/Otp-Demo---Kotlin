package com.aqua30.otpdemo.navigator

import android.content.Intent
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.FragmentActivity
import com.aqua30.otpdemo.data.impl.resrc.IRes
import com.aqua30.otpdemo.screens.home.HomeActivity
import com.aqua30.otpdemo.screens.login.LoginActivity
import javax.inject.Inject

/**
 * Class used to navigate from one page to another.
 * It uses screen code.
 * */
class NavigatorImpl @Inject constructor(
    private val activity: FragmentActivity,
    val res: IRes): Navigator {

    override fun navigateTo(screen: Screen, view: View, animationKey: String) {
        when (screen) {
            Screen.LOGIN -> {
                activity.startActivity(Intent(activity, LoginActivity::class.java), activityOptions(view,animationKey).toBundle())
            }
            Screen.HOME -> {
                activity.startActivity(Intent(activity, HomeActivity::class.java), activityOptions(view,animationKey).toBundle())
            }
        }
    }

    private fun activityOptions(view: View, animationKey: String): ActivityOptionsCompat {
        return ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                view,
                animationKey
            )
    }
}