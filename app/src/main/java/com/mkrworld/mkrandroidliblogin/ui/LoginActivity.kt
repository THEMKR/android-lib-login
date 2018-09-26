package com.mkrworld.mkrandroidliblogin.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mkrworld.mkrandroidliblogin.LoginLib
import com.mkrworld.mkrandroidliblogin.R
import com.mkrworld.mkrandroidliblogin.enums.LoginType

class LoginActivity : Activity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        findViewById<View>(R.id.activity_login_button_facebook).setOnClickListener(this)
        findViewById<View>(R.id.activity_login_button_gmail).setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id ?: -1) {
            R.id.activity_login_button_facebook -> {
                LoginLib.init(application)
                try {
                    LoginLib.Builder(this).setLoginType(LoginType.FACEBOOK).build().startLogin()
                }catch (e:Exception){
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
            }
            R.id.activity_login_button_gmail -> {
                try {
                    LoginLib.Builder(this).build()
                }catch (e:Exception){
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}