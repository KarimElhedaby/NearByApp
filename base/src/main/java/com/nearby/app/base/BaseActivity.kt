package com.nearby.app.base

import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nearby.app.Common
import com.nearby.app.Common.changeLang
import com.nearby.app.Common.showLoadingDialog
import com.nearby.app.Common.showSnackBar
import com.nearby.app.utils.inteceptor.NetworkEvent
import com.nearby.app.utils.inteceptor.NetworkState
import io.reactivex.functions.Consumer
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named


abstract class BaseActivity<P : MvpPresenter<*>> : AppCompatActivity(), MvpView {




    protected abstract val presenter: P

    protected abstract fun getLayoutResource(): Int

    protected abstract fun initLayout(savedInstanceState: Bundle?)


    protected abstract fun fullScreen(): Boolean
    protected abstract fun hideInputType(): Boolean

    private var mAlertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        if (fullScreen()) {
            Common.fullScreen(this)
        }

        setContentView(getLayoutResource())

        if (hideInputType()) {
            hideKeyboard()
        }


        initLayout(savedInstanceState)


    }


//    override fun attachBaseContext(newBase: Context?) {
//        newBase?.let {
//            super.attachBaseContext(changeLang(language, it))
//        }
//
//    }


    override fun showLoading() {
        hideLoading()
        mAlertDialog = showLoadingDialog(this)
    }

    override fun hideLoading() {
        if (mAlertDialog != null && mAlertDialog?.isShowing == true) {
            mAlertDialog?.cancel()
        }
    }


    override fun onError(resId: Int) {
        onError(getString(resId))
    }

    override fun onError(message: String) {
        showSnackBar(this, message)

    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(resId: Int) {
        showMessage(getString(resId))
    }


    override fun hideKeyboard() {
        val inputMethodManager = getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager?
        inputMethodManager?.hideSoftInputFromWindow(
            currentFocus?.windowToken, 0
        )
    }

    override fun onNetworkError() {
        Toast.makeText(this, getString(R.string.server_connect_error), Toast.LENGTH_SHORT).show()
    }


    override fun onResume() {
        super.onResume()
        //NetworkEvent.register(this,io.reactivex.functions.Consumer {  })
        NetworkEvent.register(this, Consumer {
            when (it) {
                NetworkState.NO_INTERNET -> showMessage("No Internet")
                NetworkState.NO_RESPONSE -> showMessage("No Response")
                NetworkState.UNAUTHORISED -> showMessage("UNAUTHORISED")
            }


        })
    }

    override fun onStop() {
        super.onStop()
        NetworkEvent.unregister(this)
    }


    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean = Build.VERSION.SDK_INT < Build.VERSION_CODES.M
            || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

}