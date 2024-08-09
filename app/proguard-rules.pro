# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
##--------------- OKHTTP3 ------------
#Source : https://github.com/square/okhttp/
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
##--------------- OKHTTP3 ------------


-keep class retrofit.** { *; }
-keep class retrofit2.** { *; }
-keepclasseswithmembers class * { @retrofit.http.* <methods>; }
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }


##--------------- Retrofit2 ------------
#Source : https://github.com/square/retrofit#proguard
-dontwarn okio.**
-dontwarn javax.annotation.**
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
##--------------- Retrofit2 ------------
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
-keep,allowoptimization,allowshrinking,allowobfuscation class <3>


#### Crashlytics
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

#firebase db
# Add this global rule
-keepattributes Signature

-keep public class com.google.android.gms.* { public *; }
-dontwarn com.google.android.gms.**

-ignorewarnings