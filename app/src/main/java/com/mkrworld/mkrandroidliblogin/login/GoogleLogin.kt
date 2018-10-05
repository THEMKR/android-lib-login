package com.mkrworld.mkrandroidliblogin.login

import android.app.Activity
import android.content.Intent
import android.support.annotation.NonNull
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.mkrworld.mkrandroidliblogin.callback.OnLoginListener
import com.mkrworld.mkrandroidliblogin.dto.LoginData
import com.mkrworld.mkrandroidliblogin.enums.LoginType
import com.mkrworld.mkrandroidliblogin.utils.Constants


internal class GoogleLogin : BaseLogin {
    companion object {
        private const val RC_SIGN_IN: Int = 9999999
    }

    private var permissionList: List<String>
    private var googleSignInClient: GoogleSignInClient

    /**
     * Constructor
     * @param activity
     * @param onLoginListener
     * @param loginPermissions
     */
    constructor(activity: Activity, onLoginListener: OnLoginListener?, loginPermissions: List<String>) : super(activity, onLoginListener) {
        this.permissionList = loginPermissions
        val builder = GoogleSignInOptions.Builder()
        for (permission in permissionList) {
            builder.requestScopes(Scope(permission))
        }
        googleSignInClient = GoogleSignIn.getClient(activity, builder.build())
    }

    override fun startLogin() {
        val account = GoogleSignIn.getLastSignedInAccount(activity)
        if (account == null) {
            activity.startActivityForResult(googleSignInClient.signInIntent, RC_SIGN_IN)
        } else {
            googleSignInClient.signOut().addOnCompleteListener(activity, object : OnCompleteListener<Void> {
                override fun onComplete(p0: Task<Void>) {
                    activity.startActivityForResult(googleSignInClient.signInIntent, RC_SIGN_IN)
                }
            })
        }
    }

    override fun refreshAccessToken() {
        onLoginListener?.onFindAccessTokenFailed(Exception(Constants.ERROR_MESSAGE_UNDER_DEVELOPMENT), LoginType.GOOGLE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
            var loginData = LoginData()
            loginData.sessionToken = account.id ?: ""
            loginData.name = account.displayName ?: ""
            loginData.email = account.email ?: ""
            loginData.loginType = LoginType.GOOGLE
            loginData.profilePicUrl = account.photoUrl.toString()
            onLoginListener?.onLoginSuccessful(loginData, LoginType.GOOGLE)
        } catch (error: ApiException) {
            if ((error.message ?: "").trim().equals("13:")) {
                onLoginListener?.onLoginFailed(Exception(Constants.ERROR_MESSAGE_LOGIN_CANCEL), LoginType.GOOGLE)
            } else {
                onLoginListener?.onLoginFailed(error, LoginType.GOOGLE)
            }
        }
    }
}