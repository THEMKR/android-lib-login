package com.mkrworld.mkrandroidliblogin.callback

import com.mkrworld.mkrandroidliblogin.dto.LoginData
import com.mkrworld.mkrandroidliblogin.enums.LoginType

/**
 * Callback used to know the status of startLogin process
 */
interface OnLoginListener {

    /**
     * Method to notify that startLogin process is completed successfully
     * @param loginData Data received at the Time of Login
     * @param loginType Env chosen for Login
     * @see LoginType
     */
    fun onLoginSuccessful(loginData: LoginData, loginType: LoginType)

    /**
     * Method to notify startLogin process is failed.
     * @param e Exception raised at the time of startLogin process
     * @param loginType Env chosen for Login
     * @see LoginType
     */
    fun onLoginFailed(e: Exception?, loginType: LoginType)

    /**
     * Method to notify that access-token is successfully updated
     * @param token Access token.[<ol><li>LoginType.FACEBOOK -> com.facebook.AccessToken</li><li>LoginType.G_MAIL -> com.facebook.AccessToken</li></ol>]
     * @param loginType Env chosen for Login
     * @see LoginType
     */
    fun onFindAccessTokenSuccess(token: Any?, loginType: LoginType)

    /**
     * Method to notify that access-token is successfully updated
     * @param e Exception raised at the time of finding access token
     * @param loginType Env chosen for Login
     * @see LoginType
     */
    fun onFindAccessTokenFailed(e: Exception?, loginType: LoginType)
}