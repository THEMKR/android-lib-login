package com.nazgul.library.mkrandroidliblogin.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import com.nazgul.library.mkrandroidliblogin.LoginLib
import com.nazgul.library.mkrandroidliblogin.R
import com.nazgul.library.mkrandroidliblogin.callback.OnLoginListener
import com.nazgul.library.mkrandroidliblogin.dto.LoginData
import com.nazgul.library.mkrandroidliblogin.enums.LoginType
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class LoginActivity : Activity(), View.OnClickListener {

    companion object {
        private const val TAG: String = "LoginActivity"
    }

    var loginLib: LoginLib? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginLib.init(application)
        setContentView(R.layout.activity_login)
        findViewById<View>(R.id.activity_login_button_facebook).setOnClickListener(this)
        findViewById<View>(R.id.activity_login_button_gmail).setOnClickListener(this)
        findKeyHash()
    }

    override fun onClick(view: View?) {
        when (view?.id ?: -1) {
            R.id.activity_login_button_facebook -> {
                LoginLib.init(application)
                try {
                    loginLib = LoginLib.Builder(this).setLoginType(LoginType.FACEBOOK).setPermissionList(Arrays.asList("public_profile", "email")).setInfoList(Arrays.asList("id", "name", "email")).setLoginListener(object : OnLoginListener {
                        override fun onLoginSuccessful(loginData: LoginData, loginType: LoginType) {
                            Log.e("MKR", "onLoginSuccessful() : $loginData  ${loginType.name}")
                        }

                        override fun onLoginFailed(e: Exception?, loginType: LoginType) {
                            Log.e("MKR", "onLoginFailed() : ${e?.message}  ${loginType.name}")
                        }

                        override fun onFindAccessTokenSuccess(token: Any?, loginType: LoginType) {
                            Log.e("MKR", "onFindAccessTokenSuccess() : $token  ${loginType.name}")
                        }

                        override fun onFindAccessTokenFailed(e: Exception?, loginType: LoginType) {
                            Log.e("MKR", "onFindAccessTokenFailed() : ${e?.message}  ${loginType.name}")
                        }
                    }).build()
                    loginLib?.startLogin()
                } catch (e: Exception) {
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
            }
            R.id.activity_login_button_gmail -> {
                try {
                    loginLib = LoginLib.Builder(this).setLoginType(LoginType.GOOGLE).setPermissionList(Arrays.asList("profile", "email", "openid")).setLoginListener(object : OnLoginListener {
                        override fun onLoginSuccessful(loginData: LoginData, loginType: LoginType) {
                            Log.e("MKR", "onLoginSuccessful() : $loginData  ${loginType.name}")
                        }

                        override fun onLoginFailed(e: Exception?, loginType: LoginType) {
                            Log.e("MKR", "onLoginFailed() : ${e?.message}  ${loginType.name}")
                        }

                        override fun onFindAccessTokenSuccess(token: Any?, loginType: LoginType) {
                            Log.e("MKR", "onFindAccessTokenSuccess() : $token  ${loginType.name}")
                        }

                        override fun onFindAccessTokenFailed(e: Exception?, loginType: LoginType) {
                            Log.e("MKR", "onFindAccessTokenFailed() : ${e?.message}  ${loginType.name}")
                        }
                    }).build()
                    loginLib?.startLogin()
                } catch (e: Exception) {
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loginLib?.onActivityResult(requestCode, resultCode, data)
    }

    private fun findKeyHash() {
        try {
            val info = getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("MKR", "KeyHash : " + Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "findKeyHash: " + e.message)
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, "findKeyHash: " + e.message)
        }
    }

}