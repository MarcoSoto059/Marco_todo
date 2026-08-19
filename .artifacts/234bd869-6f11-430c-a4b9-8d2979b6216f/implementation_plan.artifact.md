# Fix App Crash on Startup

The app is reportedly closing immediately upon startup. Based on the code analysis, a likely cause is the inclusion of Google Maps and AdMob without the necessary metadata in `AndroidManifest.xml`. Specifically, `play-services-maps` and `play-services-ads` require a `com.google.android.geo.API_KEY` and `com.google.android.gms.ads.APPLICATION_ID` respectively.

## User Review Required

> [!IMPORTANT]
> To properly fix the crash, valid API keys and Application IDs are required. I will use placeholder values for now to prevent the crash, but you will need to replace them with your actual keys from the Google Cloud Console and AdMob Dashboard.

## Proposed Changes

### Android Manifest

#### [MODIFY] [AndroidManifest.xml](file:///C:/Users/marco/AndroidStudioProjects/Marco_todo/app/src/main/AndroidManifest.xml)
Add the required `<meta-data>` tags for Google Maps and AdMob.

```xml
<manifest ...>
    <application ...>
        <!-- Google Maps API Key -->
        <meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="YOUR_API_KEY_HERE" />

        <!-- AdMob Application ID -->
        <meta-data
            android:name="com.google.android.gms.ads.APPLICATION_ID"
            android:value="ca-app-pub-3940256099942544~3347511713"/> <!-- Sample ID -->
        ...
    </application>
</manifest>
```

## Verification Plan

### Automated Tests
- Run `:app:assembleDebug` to ensure the project still builds.

### Manual Verification
- Deploy the app to a device or emulator and check if it still crashes on startup.
- Navigate to the "Google Services" screen to see if the app crashes when attempting to render the Map or Ad (note: Map might be blank without a real key).
