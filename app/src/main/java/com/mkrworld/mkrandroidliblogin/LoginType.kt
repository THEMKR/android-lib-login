package com.mkrworld.mkrandroidliblogin

enum class LoginType {
    NAN, FACEBOOK, G_MAIL;

    companion object {
        /**
         * Method to get the Inter-Id of Based on Login-Type
         * @param loginType
         */
        fun getLoginTypeId(loginType: LoginType): Int {
            return loginType.ordinal
        }

        /**
         * Method to get the Login_Type enum value based on the Login-Type-Integer-Id
         * @param loginId
         */
        fun getLoginType(loginId: Int): LoginType {
            val values = LoginType.values()
            for (value in values) {
                if (getLoginTypeId(value) == loginId) {
                    return value
                }
            }
            return NAN
        }
    }
}