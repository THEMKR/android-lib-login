package com.lory.library.login.login

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.lory.library.login.callback.OnLoginListener
import com.lory.library.login.dto.LoginData
import com.lory.library.login.enums.LoginType
import com.lory.library.login.utils.Constants
import org.json.JSONObject

internal class FacebookLogin : BaseLogin {
    private var callbackManager: CallbackManager = CallbackManager.Factory.create()
    private var infoList: String = ""
    private var permissionList: List<String>

    /**
     * Callback to listen GraphRequest
     */
    private val graphRequestCallBack: GraphRequest.GraphJSONObjectCallback = object : GraphRequest.GraphJSONObjectCallback {
        override fun onCompleted(json: JSONObject?, response: GraphResponse?) {
            if (json != null && response != null) {
                var loginData = LoginData()
                loginData.id = json.optString("id")
                loginData.name = json.optString("name")
                loginData.email = json.optString("email")
                loginData.loginType = LoginType.FACEBOOK
                loginData.profilePicUrl = "http://graph.facebook.com/${loginData.id}/picture?type=large"
                onLoginListener?.onLoginSuccessful(loginData, LoginType.FACEBOOK)
            } else {
                val message = response?.error?.errorMessage ?: Constants.ERROR_MESSAGE_MISCELLANEOUS
                onLoginListener?.onLoginFailed(Exception(message), LoginType.FACEBOOK)
            }
        }
    }

    /**
     * Callback to listen FacebookLogin
     */
    private val facebookResultCallback: FacebookCallback<LoginResult> = object : FacebookCallback<LoginResult> {
        override fun onSuccess(loginResult: LoginResult) {
            if (accessToken != null) {
                val graphRequest: GraphRequest = GraphRequest.newMeRequest(accessToken, graphRequestCallBack)
                val parameters = Bundle()
                parameters.putString("infoList", infoList)
                graphRequest.parameters = parameters
                graphRequest.executeAsync()
            }
        }

        override fun onCancel() {
            onLoginListener?.onLoginFailed(Exception(Constants.ERROR_MESSAGE_LOGIN_CANCEL), LoginType.FACEBOOK)
        }

        override fun onError(error: FacebookException) {
            onLoginListener?.onLoginFailed(error, LoginType.FACEBOOK)
        }
    }

    /**
     * Constructor
     * @param activity
     * @param onLoginListener
     * @param permissionList
     */
    constructor(activity: Activity, onLoginListener: OnLoginListener?, permissionList: List<String>) : super(activity, onLoginListener) {
        this.permissionList = permissionList
        this.infoList = "id, name, email"
    }

    override fun startLogin() {
        if (accessToken != null) {
            LoginManager.getInstance().logOut()
        }

        val loginButton = LoginButton(activity)
        loginButton.setReadPermissions(permissionList)
        loginButton.registerCallback(callbackManager, facebookResultCallback)
        loginButton.performClick()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun getAccessToken(): Any? {
        return accessToken
    }

    override fun refreshAccessToken() {
        if (accessToken == null) {
            AccessToken.refreshCurrentAccessTokenAsync()
            startTokenRefreshWaiter()
        } else {
            onLoginListener?.onFindAccessTokenSuccess(accessToken, LoginType.FACEBOOK)
        }
    }

    /**
     * Method to start the Task waiter to check waether the Token is refreshed or not
     */
    private fun startTokenRefreshWaiter() {
        object : AsyncTask<Void, Void, Boolean>() {

            override fun doInBackground(vararg voids: Void): Boolean? {
                var count = 100
                while (count > 0) {
                    count--
                    try {
                        Thread.sleep(50)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    if (accessToken != null) {
                        return true
                    }
                }
                return false
            }

            override fun onPostExecute(aBoolean: Boolean?) {
                super.onPostExecute(aBoolean)
                if (aBoolean != null && aBoolean) {
                    onLoginListener?.onFindAccessTokenSuccess(accessToken, LoginType.FACEBOOK)
                } else {
                    onLoginListener?.onFindAccessTokenFailed(Exception(Constants.ERROR_MESSAGE_UNABLE_TO_GET_ACCESS_TOKEN), LoginType.FACEBOOK)
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    /**
     * Method to get the Current Access Token
     *
     * @return Return current AccessToken, If not have any then Return null
     */
    private val accessToken: AccessToken?
        get() {
            val currentAccessToken: AccessToken? = AccessToken.getCurrentAccessToken()
            return if (currentAccessToken != null && !currentAccessToken.isExpired) {
                currentAccessToken
            } else null
        }
}