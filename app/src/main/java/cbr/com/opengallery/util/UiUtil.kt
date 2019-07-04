package cbr.com.opengallery.util

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

fun getScreenHeight(context: Context): Int = getDisplayMetrics(context).heightPixels

fun getScreenWidth(context: Context): Int = getDisplayMetrics(context).widthPixels

fun getColumnCount(context: Context, columnSize: Int): Int = getScreenHeight(context) / columnSize

fun getDisplayMetrics(context: Context): DisplayMetrics {
    var metrics = DisplayMetrics()
    (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(metrics)
    return metrics
}