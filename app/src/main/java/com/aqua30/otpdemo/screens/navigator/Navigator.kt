package com.aqua30.otpdemo.screens.navigator

enum class Screen {

    LOGIN,
    HOME,
    COUNTRY_SEARCH
}

interface Navigator {
    fun navigateTo(screen: Screen)
}