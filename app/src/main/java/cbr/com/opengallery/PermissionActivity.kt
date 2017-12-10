package cbr.com.opengallery

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable

/** Created by Dimitrios on 12/10/2017.*/

@SuppressLint("Registered")
open class PermissionActivity : AppCompatActivity() {
    
    protected val REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101
    protected val REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102
    
    protected lateinit var compositeDisposable: CompositeDisposable
    
    override fun onPostResume() {
        super.onPostResume()
        compositeDisposable = CompositeDisposable()
    }
    
    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }
    
    fun hasPermission(permission: String): Boolean = ActivityCompat.checkSelfPermission(this@PermissionActivity, permission) == PackageManager.PERMISSION_GRANTED
    
    fun requestPermission(permission: String, rationale: String, requestCode: Int) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            showAlertDialog(getString(R.string.title_permission_needed), rationale,
                    DialogInterface.OnClickListener { dialog, which ->
                        ActivityCompat.requestPermissions(this@PermissionActivity, arrayOf(permission), requestCode)
                    }, getString(R.string.label_ok), null, getString(R.string.label_cancel))
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        }
    }
    
    protected fun showAlertDialog(title: String?, message: String?,
                                  onPositiveButtonClickListener: DialogInterface.OnClickListener?,
                                  positiveText: String,
                                  onNegativeButtonClickListener: DialogInterface.OnClickListener?,
                                  negativeText: String) {
        AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveText, onPositiveButtonClickListener)
                .setNegativeButton(negativeText, onNegativeButtonClickListener)
                .show()
    }
    
    fun handleError(throwable: Throwable?) {
        throwable?.let { log(throwable) }
    }
}