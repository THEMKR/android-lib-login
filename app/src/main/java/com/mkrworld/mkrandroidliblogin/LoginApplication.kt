package com.mkrworld.mkrandroidliblogin

import android.app.Activity
import android.app.Application
import android.content.Intent

/**
 * Class to control the login process
 */
class LoginApplication {
    companion object {

        /**
         * This Method is called to initialize this Module.
         * user Must call this method from Application Class
         */
        fun init(application: Application) {

        }
    }

    var activity: Activity
    var loginType: LoginType

    /**
     * Constructor
     */
    constructor(activity: Activity, loginType: LoginType) {
        this.activity = activity
        this.loginType = loginType
    }

    /**
     * Method to start the Login Process
     */
    fun startLogin() {

    }

    /**
     * This method must be called from caller from Activity
     * @see Activity.onActivityResult
     * @param requestCode
     * @param resultCode
     * @param data
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }
}