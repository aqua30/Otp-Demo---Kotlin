package com.aqua30.otpdemo.navigator

import android.view.View

enum class Screen {

    LOGIN,
    HOME
}

interface Navigator {
    fun navigateTo(screen: Screen, view: View, animationKey: String)
}