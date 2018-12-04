package com.lory.library.login.login

import android.app.Activity
import com.lory.library.login.callback.OnLoginListener
import com.lory.library.login.enums.LoginType

class LoginControllerFactory {
    companion object {
        /**
         * Method to create Login Controller
         * @param loginType
         * @param activity
         * @param onLoginListener
         * @param permissionList
         */
        fun create(loginType: LoginType, activity: Activity, onLoginListener: OnLoginListener?, permissionList: List<String>): BaseLogin? {
            when (loginType) {
                LoginType.FACEBOOK -> {
                    return FacebookLogin(activity, onLoginListener, permissionList)
                }
                LoginType.GOOGLE -> {
                    return GoogleLogin(activity, onLoginListener, permissionList)
                }
            }
            return null
        }
    }
}