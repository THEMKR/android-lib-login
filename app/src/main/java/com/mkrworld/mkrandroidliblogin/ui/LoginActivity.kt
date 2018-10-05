package com.mkrworld.mkrandroidliblogin.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import com.mkrworld.mkrandroidliblogin.LoginLib
import com.mkrworld.mkrandroidliblogin.R
import com.mkrworld.mkrandroidliblogin.enums.LoginType
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
        //findKeyHash()
    }

    override fun onClick(view: View?) {
        when (view?.id ?: -1) {
            R.id.activity_login_button_facebook -> {
                LoginLib.init(application)
                try {
                    loginLib = LoginLib.Builder(this).setLoginType(LoginType.FACEBOOK).setLoginPermissionList(Arrays.asList("public_profile", "email")).setLoginInfoList(Arrays.asList("id", "name", "email")).build()
                    loginLib?.startLogin()
                } catch (e: Exception) {
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
            }
            R.id.activity_login_button_gmail -> {
                try {
                    loginLib = LoginLib.Builder(this).setLoginType(LoginType.FACEBOOK).setLoginPermissionList(Arrays.asList("public_profile", "email")).setLoginInfoList(Arrays.asList("id", "name", "email")).build()
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