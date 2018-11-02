package com.lory.library.login.login

import android.app.Activity
import android.content.Intent
import com.lory.library.login.callback.OnLoginListener

/**
 * Base class for All Login Activity
 */
internal abstract class BaseLogin {

    var activity: Activity
    var onLoginListener: OnLoginListener? = null

    /**
     * Constructor
     * @param activity
     * @param onLoginListener
     */
    constructor(activity: Activity, onLoginListener: OnLoginListener?) {
        this.activity = activity
        this.onLoginListener = onLoginListener
    }

    /**
     * Method to start Login Process
     */
    abstract fun startLogin()

    /**
     * Method to refresh the current Access Token
     */
    abstract fun refreshAccessToken()

    /**
     * Method to return the Result By the 3rd Party Lib
     */
    abstract fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}