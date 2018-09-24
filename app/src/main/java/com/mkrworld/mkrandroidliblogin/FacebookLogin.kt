package com.mkrworld.mkrandroidliblogin

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import org.json.JSONObject

internal class FacebookLogin : BaseLogin {
    private var callbackManager: CallbackManager? = null
    private var fields: String


    /**
     * Constructor
     * @param activity
     * @param loginPermissions
     * @param onLoginListener
     */
    constructor(activity: Activity, loginPermissions: List<String>, onLoginListener: OnLoginListener, fields: String) : super(activity, loginPermissions, onLoginListener) {
        this.fields = fields
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

    override fun login() {
        val loginButton = LoginButton(activity)
        loginButton.setReadPermissions(loginPermissions)
        callbackManager = CallbackManager.Factory.create()
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                if (accessToken != null) {
                    val graphRequest: GraphRequest = GraphRequest.newMeRequest(accessToken, object : GraphRequest.GraphJSONObjectCallback {
                        override fun onCompleted(json: JSONObject?, response: GraphResponse?) {
                            if (json != null && response != null) {
                                onLoginListener.onLoginSuccess(json)
                            } else {
                                onLoginListener.onLoginFailed()
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
                onLoginListener.onLoginFailed()
            }

            override fun onError(error: FacebookException) {
                onLoginListener.onLoginError(error.message ?: error.localizedMessage ?: Constants.MISCELLANEOUS_ERROR_MESSAGE)
            }
        })
        loginButton.performClick()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    override fun getAccessToken() {
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
                        onLoginListener.onLoginAccessToken(accessToken!!, LoginType.FACEBOOK)
                    } else {
                        onLoginListener.onLoginAccessTokenFailed(LoginType.FACEBOOK)
                    }
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        } else {
            onLoginListener.onLoginAccessToken(accessToken!!, LoginType.FACEBOOK)
        }
    }
}