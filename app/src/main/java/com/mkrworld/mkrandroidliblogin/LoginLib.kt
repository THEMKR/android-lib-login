package com.mkrworld.mkrandroidliblogin

import android.app.Activity
import android.app.Application
import android.content.Intent
import com.mkrworld.mkrandroidliblogin.callback.OnLoginListener
import com.mkrworld.mkrandroidliblogin.enums.LoginType
import com.mkrworld.mkrandroidliblogin.login.BaseLogin
import com.mkrworld.mkrandroidliblogin.login.facebook.FacebookLogin
import com.mkrworld.mkrandroidliblogin.utils.Constants
import com.mkrworld.mkrandroidliblogin.utils.LibPrefData

/**
 * Class to control the startLogin process. Plz called the init Method from Application to initialized this Lib
 */
class LoginLib {
    companion object {

        /**
         * This Method is called to initialize this Module.
         * user Must call this method from Application Class
         */
        fun init(application: Application) {
            LibPrefData.setBoolean(application, LibPrefData.Key.IS_LIB_INITIALIZE, true)
        }
    }

    private var baseLogin: BaseLogin

    /**
     * Constructor
     * @param activity
     */
    private constructor(baseLogin: BaseLogin) {
        this.baseLogin = baseLogin
    }

    /**
     * Method to start the Login Process
     */
    fun startLogin() {
        baseLogin.startLogin()
    }

    /**
     * Method to refresh access Token
     */
    fun refreshAccessToken() {
        baseLogin.refreshAccessToken()
    }

    /**
     * This method must be called from caller from Activity
     * @see Activity.onActivityResult
     * @param requestCode
     * @param resultCode
     * @param data
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        baseLogin.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * Class to Build the LoginLib
     */
    class Builder {
        private var activity: Activity
        private var loginType: LoginType = LoginType.NAN
        private var loginListener: OnLoginListener? = null
        private var loginInfoList: ArrayList<String>
        private var loginPermissionList: ArrayList<String>

        /**
         * Constructor
         * @param activity
         */
        constructor(activity: Activity) {
            this.activity = activity
            loginInfoList = ArrayList()
            loginPermissionList = ArrayList()
        }

        /**
         * Method to set the LoginType
         * @param loginType
         */
        fun setLoginType(loginType: LoginType): Builder {
            this.loginType = loginType
            return this
        }

        /**
         * Method to set the LoginType
         * @param loginType
         */
        fun setLoginListener(loginListener: OnLoginListener): Builder {

            this.loginListener = loginListener
            return this
        }

        /**
         * Method to set the Login Permission Requested by the Client
         * @param loginPermissionList
         */
        fun setLoginPermissionList(loginPermissionList: List<String>): Builder {
            this.loginPermissionList.clear()
            this.loginPermissionList.addAll(loginPermissionList)
            return this
        }

        /**
         * Method to set the List of Info requested by the client
         * @param loginInfoList
         */
        fun setLoginInfoList(loginInfoList: List<String>): Builder {
            this.loginPermissionList.clear()
            this.loginPermissionList.addAll(loginPermissionList)
            return this
        }

        /**
         * Method to build the MKR Login
         * @throws Exception Caller must handel the exception
         */
        fun build(): LoginLib {
            if (!LibPrefData.getBoolean(activity, LibPrefData.Key.IS_LIB_INITIALIZE)) {
                throw Exception(Constants.ERROR_MESSAGE_LOGIN_LIB_NOT_INIT)
            }
            return LoginLib(getLoginObject() ?: throw Exception(Constants.ERROR_MESSAGE_LOGIN_TYPE_NOT_SET))
        }


        /**
         * Method to get the Login Object of the Class associate with the LoginType
         */
        private fun getLoginObject(): BaseLogin? {
            when (loginType) {
                LoginType.FACEBOOK -> {
                    return FacebookLogin(activity, loginListener, loginPermissionList, loginInfoList)
                }
            }
            return null
        }
    }
}