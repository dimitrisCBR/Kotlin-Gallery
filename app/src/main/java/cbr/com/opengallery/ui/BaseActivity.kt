package cbr.com.opengallery.ui

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import cbr.com.opengallery.R
import cbr.com.opengallery.util.log
import io.reactivex.disposables.CompositeDisposable


@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    protected val REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101
    protected val REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102

    protected val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun hasPermission(permission: String): Boolean = ActivityCompat.checkSelfPermission(this@BaseActivity, permission) == PackageManager.PERMISSION_GRANTED

    fun requestPermission(permission: String, rationale: String, requestCode: Int) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            showAlertDialog(getString(R.string.title_permission_needed), rationale,
                    DialogInterface.OnClickListener { dialog, which ->
                        ActivityCompat.requestPermissions(this@BaseActivity, arrayOf(permission), requestCode)
                    }, getString(R.string.label_ok),
                    DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                        finish()
                    }, getString(R.string.label_cancel))
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
                .setCancelable(false)
                .show()
    }

    fun handleError(throwable: Throwable?) {
        throwable?.let { log(throwable) }
    }
}