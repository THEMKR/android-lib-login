package com.lory.library.login.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Parcelable
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.Toast
import com.lory.library.login.LoginLib
import com.lory.library.login.R
import com.lory.library.login.callback.OnLoginListener
import com.lory.library.login.dto.LoginData
import com.lory.library.login.enums.LoginType
import com.lory.library.login.utils.Constants
import java.security.MessageDigest
import java.util.*

class LoginActivity : Activity(), View.OnClickListener, OnLoginListener {

    companion object {
        private const val TAG: String = "LoginActivity"
    }

    var loginLib: LoginLib? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginLib.init(application)
        setContentView(R.layout.activity_login)
        val parent = findViewById<View>(R.id.activity_login_parent)
        val buttonFb = findViewById<View>(R.id.activity_login_button_facebook)
        val buttonGoogle = findViewById<View>(R.id.activity_login_button_gmail)
        // SET DIMENSION
        val displayMetrics = resources.displayMetrics
        var outerPadding = (displayMetrics.widthPixels.toFloat() * 0.02F).toInt()
        var buttonWidth = displayMetrics.widthPixels - (6 * outerPadding)
        var buttonHeight = (buttonWidth.toFloat() * 0.20).toInt()
        parent.setPadding(3 * outerPadding, outerPadding, 3 * outerPadding, outerPadding)
        buttonFb.layoutParams.width = buttonWidth
        buttonFb.layoutParams.height = buttonHeight
        buttonGoogle.layoutParams.width = buttonWidth
        buttonGoogle.layoutParams.height = buttonHeight
        parent.layoutParams.height = (buttonHeight * 3) + (outerPadding * 1)

        // ANIMATE OPTION
        var type = Animation.RELATIVE_TO_SELF
        val animation = TranslateAnimation(type, 0F, type, 0F, type, 1F, type, 0F)
        animation.duration = 500
        parent.animation = animation
        animation.start()

        // SET CLICK LISTENER
        buttonFb.setOnClickListener(this)
        buttonGoogle.setOnClickListener(this)
        findViewById<View>(R.id.activity_login_empty).setOnClickListener(this)

        // INITIATE DIRECT LOGIN
        val loginType = LoginType.getLoginType(intent?.getIntExtra(LoginLib.EXTRA_LOGIN_PROVIDER, -1) ?: -1)
        if (!loginType.equals(LoginType.NAN)) {
            parent.visibility = View.GONE
            startLogin(loginType)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id ?: -1) {
            R.id.activity_login_button_facebook -> {
                startLogin(LoginType.FACEBOOK)
            }
            R.id.activity_login_button_gmail -> {
                startLogin(LoginType.GOOGLE)
            }
            else -> {
                val returnIntent = Intent()
                returnIntent.putExtra(LoginLib.EXTRA_ERROR_MESSAGE, Constants.ERROR_MESSAGE_LOGIN_TYPE_NOT_SET)
                setResult(Activity.RESULT_CANCELED, returnIntent)
                finish()
            }
        }
    }

    override fun onLoginSuccessful(loginData: LoginData, loginType: LoginType) {
        Log.e("MKR", "onLoginSuccessful() : $loginData  ${loginType.name}")
        val returnIntent = Intent()
        returnIntent.putExtra(LoginLib.EXTRA_REQUEST_TYPE, LoginLib.REQUEST_TYPE_LOGIN)
        returnIntent.putExtra(LoginLib.EXTRA_LOGIN_DATA, loginData)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    override fun onLoginFailed(e: Exception?, loginType: LoginType) {
        Log.e("MKR", "onLoginFailed() : ${e?.message}  ${loginType.name}")
        throughErrorIntent(e, loginType, LoginLib.REQUEST_TYPE_LOGIN)
    }

    override fun onFindAccessTokenSuccess(token: Any?, loginType: LoginType) {
        Log.e("MKR", "onFindAccessTokenSuccess() : $token  ${loginType.name}")
        try {
            val returnIntent = Intent()
            returnIntent.putExtra(LoginLib.EXTRA_REQUEST_TYPE, LoginLib.REQUEST_TYPE_REFRESH_TOKEN)
            returnIntent.putExtra(LoginLib.EXTRA_REFRESHED_ACCESS_TOKEN, token as Parcelable)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        } catch (e: Exception) {
            throughErrorIntent(e, loginType, LoginLib.REQUEST_TYPE_REFRESH_TOKEN)
        }
    }

    override fun onFindAccessTokenFailed(e: Exception?, loginType: LoginType) {
        Log.e("MKR", "onFindAccessTokenFailed() : ${e?.message}  ${loginType.name}")
        throughErrorIntent(e, loginType, LoginLib.REQUEST_TYPE_REFRESH_TOKEN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loginLib?.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * Method to find the keyhash
     */
    private fun findKeyHash() {
        try {
            val info = getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("MKR", "KeyHash : " + Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "findKeyHash: " + e.message)
        }
    }

    /**
     * Method to start login
     * @param loginType
     */
    private fun startLogin(loginType: LoginType) {
        try {
            val builder = LoginLib.Builder(this)
            builder.setLoginType(loginType)
            builder.setLoginListener(this)
            builder.setPermissionList(intent?.getStringArrayListExtra(LoginLib.EXTRA_PERMISSION_ARRAY_LIST) ?: loginType.defaultPermissionList)
            loginLib = builder.build()
            loginLib?.startLogin()
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Method to start refreshing Token
     * @param loginType
     */
    private fun startRefreshToken(loginType: LoginType) {
        try {
            val builder = LoginLib.Builder(this)
            builder.setLoginType(loginType)
            builder.setLoginListener(this)
            builder.setPermissionList(intent?.getStringArrayListExtra(LoginLib.EXTRA_PERMISSION_ARRAY_LIST) ?: loginType.defaultPermissionList)
            loginLib = builder.build()
            loginLib?.refreshAccessToken()
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Method to return Error Intent
     * @param exception
     * @param loginType
     * @param requestType
     */
    private fun throughErrorIntent(exception: Exception?, loginType: LoginType, requestType: Int) {
        val returnIntent = Intent()
        returnIntent.putExtra(LoginLib.EXTRA_ERROR_MESSAGE, exception?.message ?: Constants.ERROR_MESSAGE_MISCELLANEOUS)
        returnIntent.putExtra(LoginLib.EXTRA_REQUEST_TYPE, requestType)
        setResult(Activity.RESULT_CANCELED, returnIntent)
        finish()
    }
}