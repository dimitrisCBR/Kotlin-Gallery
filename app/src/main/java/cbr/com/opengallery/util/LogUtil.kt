package cbr.com.opengallery.util

import android.util.Log
import cbr.com.opengallery.BuildConfig

fun log(tag: String, throwable: Throwable) {
    if (BuildConfig.DEBUG) {
        Log.e(tag, throwable.localizedMessage)
    }
}

fun log(tag: String, message: String) {
    if (BuildConfig.DEBUG) {
        Log.e(tag, message)
    }
}

fun log(throwable: Throwable) {
    log("OG", throwable)
}

fun log(message: String) {
    log("OG", message)
}