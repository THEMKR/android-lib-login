package com.mkrworld.mkrandroidliblogin.utils

internal class Constants {
    companion object {
        const val ERROR_MESSAGE_MISCELLANEOUS = "Login Failed due-to miscellaneous error"
        const val ERROR_MESSAGE_UNABLE_TO_GET_ACCESS_TOKEN = "Unable to refresh the Access-Token. Plz request again for Access-Token or Re-Initiate the Login Process"
        const val ERROR_MESSAGE_UNDER_DEVELOPMENT = "Functionality is in development phase"
        const val ERROR_MESSAGE_LOGIN_CANCEL = "Login Failed due-to user cancel the process"
        const val ERROR_MESSAGE_LOGIN_LIB_NOT_INIT = "LoginLib is not initialized. Plz initialized the LoginLib from Application Class"
        const val ERROR_MESSAGE_LOGIN_TYPE_NOT_SET = "LoginType is not set. Plz set the LoginType before building LoginLib Object"
    }
}