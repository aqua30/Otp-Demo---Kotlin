package com.aqua30.otpdemo.data.impl.prefs

interface IPref {

    fun put(key: String, value: Any)
    fun str(key: String, default: String = ""): String
    fun long(key: String, default: Long = -1): Long
    fun float(key: String, default: Float = -1f): Float
    fun int(key: String, default: Int = -1): Int
    fun bool(key: String, default: Boolean = false): Boolean
    fun clear()
}