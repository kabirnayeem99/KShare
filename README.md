# KShare

A simple Kotlin Multiplatform library to share text and files using the native share dialogs on
Android and iOS. Integrates smoothly with Compose Multiplatform.

---

## Features

* Share plain text and files easily.
* Supports sharing by file bytes or file path.
* Compose-friendly API with `LocalContentSharer` and `ProvideContentSharer`.
* Native implementation using Android `Intent.ACTION_SEND` and iOS `UIActivityViewController`.

---

## Installation

Add the library to your KMP project dependencies.

---

## Usage

### Setup provider in your Compose hierarchy

```kotlin
@Composable
fun App() {
    ProvideContentSharer{
        YourAppContent()
    }
}
```

### Share text example

```kotlin
val contentSharer = LocalContentSharer.current

Button(onClick = {
    contentSharer.shareText(
        "Stop the attacks on Gaza!\nTake action for Palestine",
        "Share Your Support for Gaza"
    )
}) {
    Text("Share Text")
}
```

### Share file example

```kotlin
val contentSharer = LocalContentSharer.current

val fileBytes: ByteArray = // load your file bytes here
    contentSharer.shareFile("document.pdf", fileBytes, "application/pdf", "Share File")
```

---

## Android Setup

Add this to your app’s `AndroidManifest.xml`:

```xml

<provider android:name="androidx.core.content.FileProvider"
    android:authorities="${applicationId}.fileprovider" android:exported="false"
    android:grantUriPermissions="true">
    <meta-data android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/file_paths" />
</provider>
```

Create `res/xml/file_paths.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<paths xmlns:android="http://schemas.android.com/apk/res/android">
    <cache-path name="cache" path="." />
</paths>
```

---

## Notes

* The library requires the host app to configure `FileProvider`.
* On iOS, pass the current `UIViewController` to `ContentSharer`.
* Supports Compose Multiplatform seamlessly.

---

## License

MIT License

---

