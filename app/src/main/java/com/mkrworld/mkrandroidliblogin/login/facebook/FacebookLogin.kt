package com.mkrworld.mkrandroidliblogin.login.facebook

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.mkrworld.mkrandroidliblogin.callback.OnLoginListener
import com.mkrworld.mkrandroidliblogin.enums.LoginType
import com.mkrworld.mkrandroidliblogin.login.BaseLogin
import com.mkrworld.mkrandroidliblogin.utils.Constants
import org.json.JSONObject

internal class FacebookLogin : BaseLogin {
    private var callbackManager: CallbackManager? = null
    private var fields: String
    private var loginPermissions: List<String>


    /**
     * Constructor
     * @param activity
     * @param onLoginListener
     * @param loginPermissions
     * @param fieldsList
     */
    constructor(activity: Activity, onLoginListener: OnLoginListener?, loginPermissions: List<String>, fieldsList: List<String>) : super(activity, onLoginListener) {
        this.loginPermissions = loginPermissions
        val builder = StringBuilder("")
        if (fieldsList.isNotEmpty()) {
            for (field in fieldsList) {
                builder.append("$field,")
            }
        }
        this.fields = builder.substring(0, builder.length - 1)
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

    override fun startLogin() {
        val loginButton = LoginButton(activity)
        loginButton.setReadPermissions(loginPermissions)
        callbackManager = CallbackManager.Factory.create()
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                if (accessToken != null) {
                    val graphRequest: GraphRequest = GraphRequest.newMeRequest(accessToken, object : GraphRequest.GraphJSONObjectCallback {
                        override fun onCompleted(json: JSONObject?, response: GraphResponse?) {
                            if (json != null && response != null) {

                            } else {
                                val message = response?.error?.errorMessage ?: Constants.ERROR_MESSAGE_MISCELLANEOUS
                                onLoginListener?.onLoginFailed(Exception(message), LoginType.FACEBOOK)
                            }
                        }
                    })
                    val parameters = Bundle()
                    parameters.putString("fields", fields)
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
        })
        loginButton.performClick()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    override fun refreshAccessToken() {
        if (accessToken == null) {
            AccessToken.refreshCurrentAccessTokenAsync()
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
        } else {
            onLoginListener?.onFindAccessTokenSuccess(accessToken, LoginType.FACEBOOK)
        }
    }
}