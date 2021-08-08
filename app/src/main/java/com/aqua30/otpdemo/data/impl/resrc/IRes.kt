package com.aqua30.otpdemo.data.impl.resrc

import android.graphics.drawable.Drawable

interface IRes {

    fun str(resId: Int): String
    fun color(resId: Int): Int
    fun drawable(resId: Int): Drawable?
}