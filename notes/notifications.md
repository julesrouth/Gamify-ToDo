# Notifications

## Front End
### Firebase Credentials: 
https://documentation.onesignal.com/docs/android-firebase-credentials
 Project GamePlan has the JSON File that One Signal uses
 
### One Signal
 Project Name: GamePlan
 Unlimited push notifications
 Uses a Firebase Project

 AP_ID: 913ea4fc-8967-4cbb-9d67-19f7d3a25957

 Android SDK Setup
 https://documentation.onesignal.com/docs/android-sdk-setup
 1. Add this dependency to app/build.gradle.kts
 implementation("com.onesignal:OneSignal:[5.0.0, 5.99.99]")
 2. Press sync now in the badge that comes up
 3. Add this to the onCreate method in the Application class
 ```java
import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import com.onesignal.OneSignal
import com.onesignal.debug.LogLevel

// NOTE: Replace the below with your own ONESIGNAL_APP_ID
const val ONESIGNAL_APP_ID = "########-####-####-####-############"
  
class ApplicationClass : Application() {
   override fun onCreate() {
      super.onCreate()
        
      // Verbose Logging set to help debug issues, remove before releasing your app.
      OneSignal.Debug.logLevel = LogLevel.VERBOSE

      // OneSignal Initialization
      OneSignal.initWithContext(this, ONESIGNAL_APP_ID)

      // requestPermission will show the native Android notification permission prompt.
      // NOTE: It's recommended to use a OneSignal In-App Message to prompt instead.
      CoroutineScope(Dispatchers.IO).launch {
         OneSignal.Notifications.requestPermission(true)
      }
   }
}
 ```

 4. Here is link to customize notification: https://documentation.onesignal.com/docs/android-notification-icons

 5. We can send messages from the dashboard
 https://documentation.onesignal.com/docs/sending-notifications

## Back End

