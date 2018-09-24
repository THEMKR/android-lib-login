package com.mkrworld.mkrandroidliblogin

import com.facebook.GraphResponse
import org.json.JSONObject

interface OnLoginListener {
    /**
     * Method to notify that the login is done successfully
     *
     * @param jsonObject
     */
    fun onLoginSuccess(jsonObject: JSONObject)

    /**
     * Method to notify the current Access Token
     *
     * @param accessToken
     * @param loginType
     */
    fun onLoginAccessToken(accessToken: Any, loginType: LoginType)

    /**
     * Method to notify the current Access Token Is Failed
     *
     * @param loginType
     */
    fun onLoginAccessTokenFailed(loginType: LoginType)

    /**
     * Method to notify that there is an failure occur at the time of Login
     */
    fun onLoginFailed()

    /**
     * Method to notify that there is an failure occur at the time of login
     * @param errorMessage
     */
    fun onLoginError(errorMessage: String)
}