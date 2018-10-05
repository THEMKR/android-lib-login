package com.mkrworld.mkrandroidliblogin.dto

import com.mkrworld.mkrandroidliblogin.enums.LoginType

class LoginData {

    /**
     * Unique Id of user define by Login Env
     */
    var sessionToken: String = ""

    /**
     * User Name
     */
    var name: String = ""
        get() {
            return field.trim()
        }
        set(value) {
            field = value.trim()
        }

    /**
     * User Email Id
     */
    var email: String = ""
        get() {
            return field.trim()
        }
        set(value) {
            field = value.trim()
        }

    /**
     * User Profile Pic
     */
    var profilePicUrl: String = ""
        get() {
            return field.trim()
        }
        set(value) {
            field = value.trim()
        }

    /**
     * Login Client.
     * @see LoginType
     */
    var loginType: LoginType = LoginType.NAN

    override fun toString(): String {
        return "${super.toString()} TOKEN:$sessionToken, NAME:$name, EMAIL:$email, PROFILE-PIC:$profilePicUrl, LOGIN-TYPE:${loginType.name}"
    }
}