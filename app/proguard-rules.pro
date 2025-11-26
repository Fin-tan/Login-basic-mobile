# Add project specific ProGuard rules here.

# Keep SQLite classes
-keep class android.database.sqlite.** { *; }
-keep class android.database.** { *; }

# Keep your model classes
-keep class com.example.loginbasic.User { *; }
-keep class com.example.loginbasic.DatabaseHelper { *; }

# Keep Activities
-keep class com.example.loginbasic.login { *; }
-keep class com.example.loginbasic.signup { *; }
-keep class com.example.loginbasic.Home { *; }

# Keep Android components
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# Keep View constructors
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}