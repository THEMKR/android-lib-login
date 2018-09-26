package com.mkrworld.mkrandroidliblogin.ui

import android.app.Application
import com.facebook.FacebookSdk

class LoginApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FacebookSdk.sdkInitialize(this)
    }
}