package com.lory.library.login.dto

import android.os.Parcel
import android.os.Parcelable
import com.lory.library.login.enums.LoginType

class LoginData : Parcelable {

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

    constructor(parcel: Parcel) {
        sessionToken = parcel.readString()
        name = parcel.readString()
        email = parcel.readString()
        profilePicUrl = parcel.readString()
        loginType = LoginType.getLoginType(parcel.readInt())
    }

    constructor() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(sessionToken)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(profilePicUrl)
        parcel.writeInt(LoginType.getLoginTypeId(loginType))
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "${super.toString()} TOKEN:$sessionToken, NAME:$name, EMAIL:$email, PROFILE-PIC:$profilePicUrl, LOGIN-TYPE:${loginType.name}"
    }

    companion object CREATOR : Parcelable.Creator<LoginData> {
        override fun createFromParcel(parcel: Parcel): LoginData {
            return LoginData(parcel)
        }

        override fun newArray(size: Int): Array<LoginData?> {
            return arrayOfNulls(size)
        }
    }
}