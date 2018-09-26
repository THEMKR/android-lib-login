package com.mkrworld.mkrandroidliblogin.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.mkrworld.mkrandroidliblogin.R

class LoginActivity : Activity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        findViewById<View>(R.id.activity_login_button_facebook).setOnClickListener(this)
        findViewById<View>(R.id.activity_login_button_gmail).setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onClick(view: View?) {
        when (view?.id ?: -1) {
            R.id.activity_login_button_facebook -> {

            }
            R.id.activity_login_button_gmail -> {

            }
        }
    }

}