# MKR-ANDROID-LOGIN-LIB

#   AndroidManifest.xml
		<uses-permission android:name="android.permission.INTERNET" />
	    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

        <activity
            android:name="com.facebook.FacebookActivity"
            android:configChanges="keyboard|keyboardHidden|screenLayout|screenSize|orientation"
            android:label="<app_name>"
            android:theme="@android:style/Theme.Translucent.NoTitleBar"
            tools:replace="android:theme" />

        <meta-data
            android:name="com.facebook.sdk.ApplicationId"
            android:value="<facebook_app_id>" />

        <provider
            android:name="com.facebook.FacebookContentProvider"
            android:authorities="com.facebook.app.FacebookContentProvider.<app_pkg>"
            android:exported="true" />        

#	Project Level Gradle
		repositories {
			maven {
				url "https://api.bitbucket.org/1.0/repositories/THEMKR/android-lib-login/raw/releases"
				credentials {
					username 'THEMKR'
					password '<PASSWORD>'
				}
			}
		}

#	APP Level Gradle
		implementation 'com.lory.library:login:1.0.0'
        implementation 'com.facebook.android:facebook-login:4.35.0'
        implementation 'com.google.android.gms:play-services-auth:16.0.1'
        
        
#   USE
            val builder = LoginLib.Builder(Activity)
            builder.setLoginType(LoginType.<type>)
            builder.setLoginListener(com.lory.library.login.callback.OnLoginListener)
            builder.setPermissionList(ArrayList<String> permissionList)
            loginLib = builder.build()
            loginLib?.startLogin()    