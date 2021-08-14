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
import com.aqua30.otpdemo.navigator.Navigator
import com.aqua30.otpdemo.navigator.Screen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity: BaseActivity() {

    /* binding objects */
    private lateinit var binding: HomeBinding

    /* dependency objects */
    @Inject lateinit var navigor: Navigator
    @Inject lateinit var pref: IPref
    @Inject lateinit var res: IRes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewBinding as HomeBinding
        binding.includedHeader.viewTextLoginHeader.text = getString(R.string.header_home)
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
        dialogView.findViewById<View>(R.id.view_image_logout).setOnClickListener {
            pref.put(KEY_USER_LOGIN, false)
            navigor.navigateTo(Screen.LOGIN, binding.viewImageSmartphone, res.str(R.string.image_banner_transition))
        }
        dialogBuilder.show()
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}