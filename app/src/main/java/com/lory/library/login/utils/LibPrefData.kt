package com.lory.library.login.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor


class LibPrefData {
    companion object {
        private val STORE = "LOGIN_LIB"

        //==================================================================================================================
        //==================================================================================================================
        //==================================================================================================================

        /**
         * Method to get Boolean Value
         *
         * @param context
         * @param key Pref Key
         * @param defaultValue
         * @return default defaultValue
         */
        fun getBoolean(context: Context, key: String, defaultValue: Boolean): Boolean {
            try {
                return getShearedPreference(context).getBoolean(key, defaultValue)
            } catch (e: Exception) {
                return defaultValue
            }
        }

        /**
         * Method to get Boolean Value
         *
         * @param context
         * @param key Pref Key
         * @return default value FALSE
         */
        fun getBoolean(context: Context, key: String): Boolean {
            return getBoolean(context, key, false)
        }

        /**
         * Method to set Boolean Value
         *
         * @param context
         * @param key Pref Key
         * @param value Boolean value set for key
         */
        fun setBoolean(context: Context, key: String, value: Boolean) {
            getShearedPreferenceEditor(context).putBoolean(key, value).commit()
        }

        //==================================================================================================================
        //==================================================================================================================
        //==================================================================================================================

        /**
         * Method to get String Value
         *
         * @param context
         * @param key Pref Key
         * @param defaultValue
         * @return default defaultValue
         */
        fun getString(context: Context, key: String, defaultValue: String): String {
            try {
                return getShearedPreference(context).getString(key, defaultValue)
            } catch (e: Exception) {
                return defaultValue
            }
        }

        /**
         * Method to get String Value
         *
         * @param context
         * @param key Pref Key
         * @return default value ""
         */
        fun getString(context: Context, key: String): String {
            return getString(context, key, "")
        }

        /**
         * Method to set String Value
         *
         * @param context
         * @param key Pref Key
         * @param value String value set for key
         */
        fun setString(context: Context, key: String, value: String) {
            getShearedPreferenceEditor(context).putString(key, value).commit()
        }

        //==================================================================================================================
        //==================================================================================================================
        //==================================================================================================================

        /**
         * Method to get Float Value
         *
         * @param context
         * @param key Pref Key
         * @param defaultValue
         * @return default defaultValue
         */
        fun getFloat(context: Context, key: String, defaultValue: Float): Float {
            try {
                return getShearedPreference(context).getFloat(key, defaultValue)
            } catch (e: Exception) {
                return defaultValue
            }
        }

        /**
         * Method to get Float Value
         *
         * @param context
         * @param key Pref Key
         * @return default value ""
         */
        fun getFloat(context: Context, key: String): Float {
            return getFloat(context, key, 0F)
        }

        /**
         * Method to set Float Value
         *
         * @param context
         * @param key Pref Key
         * @param value Float value set for key
         */
        fun setFloat(context: Context, key: String, value: Float) {
            getShearedPreferenceEditor(context).putFloat(key, value).commit()
        }

        //==================================================================================================================
        //==================================================================================================================
        //==================================================================================================================

        /**
         * Method to get Int Value
         *
         * @param context
         * @param key Pref Key
         * @param defaultValue
         * @return default defaultValue
         */
        fun getInt(context: Context, key: String, defaultValue: Int): Int {
            try {
                return getShearedPreference(context).getInt(key, defaultValue)
            } catch (e: Exception) {
                return defaultValue
            }
        }

        /**
         * Method to get Int Value
         *
         * @param context
         * @param key Pref Key
         * @return default value ""
         */
        fun getInt(context: Context, key: String): Int {
            return getInt(context, key, 0)
        }

        /**
         * Method to set Int Value
         *
         * @param context
         * @param key Pref Key
         * @param value Int value set for key
         */
        fun setInt(context: Context, key: String, value: Int) {
            getShearedPreferenceEditor(context).putInt(key, value).commit()
        }

        //==================================================================================================================
        //==================================================================================================================
        //==================================================================================================================

        /**
         * Method to get Long Value
         *
         * @param context
         * @param key Pref Key
         * @param defaultValue
         * @return default defaultValue
         */
        fun getLong(context: Context, key: String, defaultValue: Long): Long {
            try {
                return getShearedPreference(context).getLong(key, defaultValue)
            } catch (e: Exception) {
                return defaultValue
            }
        }

        /**
         * Method to get Long Value
         *
         * @param context
         * @param key Pref Key
         * @return default value ""
         */
        fun getLong(context: Context, key: String): Long {
            return getLong(context, key, 0L)
        }

        /**
         * Method to set Long Value
         *
         * @param context
         * @param key Pref Key
         * @param value Long value set for key
         */
        fun setLong(context: Context, key: String, value: Long) {
            getShearedPreferenceEditor(context).putLong(key, value).commit()
        }

        //==================================================================================================================
        //==================================================================================================================
        //==================================================================================================================

        /**
         * Method to clear the Data Store
         *
         * @param context
         */
        fun clearStore(context: Context) {
            getShearedPreferenceEditor(context).clear().commit()
        }

        /**
         * Method to return the Data Store Prefference
         *
         * @param context
         * @return
         */
        private fun getShearedPreference(context: Context): SharedPreferences {
            return context.getSharedPreferences(STORE, Context.MODE_PRIVATE)
        }

        /**
         * caller to commit this editor
         *
         * @param context
         * @return Editor
         */
        private fun getShearedPreferenceEditor(context: Context): Editor {
            return getShearedPreference(context).edit()
        }
    }

    /**
     *Preference Keys
     */
    class Key {
        companion object {
            const val IS_LIB_INITIALIZE = "LIB_INITIALIZE"
        }
    }
}