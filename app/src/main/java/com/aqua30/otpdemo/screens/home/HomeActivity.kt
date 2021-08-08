package com.aqua30.otpdemo.screens.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.viewbinding.ViewBinding
import com.aqua30.otpdemo.base.BaseActivity
import com.aqua30.otpdemo.data.KEY_USER_LOGIN
import com.aqua30.otpdemo.data.impl.prefs.IPref
import com.aqua30.otpdemo.data.impl.resrc.IRes
import com.aqua30.otpdemo.koltin.R
import com.aqua30.otpdemo.koltin.databinding.HomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity: BaseActivity() {

    @Inject lateinit var pref: IPref
    @Inject lateinit var res: IRes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val homeBinding: HomeBinding = viewBinding as HomeBinding
        homeBinding.includedHeader.viewTextLoginHeader.text = getString(R.string.header_home)
    }

    override fun binding(): ViewBinding {
        return HomeBinding.inflate(layoutInflater)
    }

    fun onLogout(view: View) {
        showLogoutWarning()
    }

    private fun showLogoutWarning() {
        val dialogBuilder = AlertDialog.Builder(this)
        val dialogView: View = layoutInflater.inflate(R.layout.logout_warning, null)
        dialogBuilder.setView(dialogView)
        dialogView.findViewById<View>(R.id.view_image_logout).setOnClickListener { v: View? ->
            pref.put(KEY_USER_LOGIN, false)
            /*val activityOptionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    viewAppLogoImage,
                    resourceMapper.getString(R.string.image_logo_transition)
                )
            val `in` = Intent(this, LoginActivity::class.java)
            startActivity(`in`, activityOptionsCompat.toBundle())*/
            finish()
        }
        dialogBuilder.show()
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}