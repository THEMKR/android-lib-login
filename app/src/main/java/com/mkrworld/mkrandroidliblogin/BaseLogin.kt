package com.mkrworld.mkrandroidliblogin

import android.app.Activity
import android.content.Intent

/**
 * Base class for All Login Activity
 */
internal abstract class BaseLogin {

    var activity: Activity
    var loginPermissions: List<String>
    var onLoginListener: OnLoginListener

    /**
     * Constructor
     * @param activity
     * @param loginPermissions
     * @param onLoginListener
     */
    constructor(activity: Activity, loginPermissions: List<String>, onLoginListener: OnLoginListener) {
        this.activity = activity
        this.loginPermissions = loginPermissions
        this.onLoginListener = onLoginListener
    }

    /**
     * Method to start Login Process
     */
    abstract fun login()

    /**
     * Method to get the current Access Token
     */
    abstract fun getAccessToken()

    /**
     * Method to return the Result By the 3rd Party Lib
     */
    abstract fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}