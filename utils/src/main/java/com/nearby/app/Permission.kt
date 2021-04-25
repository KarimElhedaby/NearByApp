package com.nearby.app

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import com.nearby.app.utils.R
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener


object Permission {


    interface Callback {
        fun isPermissionAccepted(isAccepted: Boolean?)
    }

    fun checkStorageAccessPermission(activity: Activity, callback: Callback) {
        Dexter.withActivity(activity)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    callback.isPermissionAccepted(report?.areAllPermissionsGranted())
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            })
            .onSameThread()
            .check()
    }


    @SuppressLint("InlinedApi")
    fun checkLocationAccessPermission(activity: Activity?, callback: Callback) {
        Dexter.withActivity(activity)
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    callback.isPermissionAccepted(report?.areAllPermissionsGranted())
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            })
            .onSameThread()
            .check()
    }

    @SuppressLint("InlinedApi")
    fun checkLocationAccessFinePermission(activity: Activity?, callback: Callback) {
        Dexter.withActivity(activity)
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    callback.isPermissionAccepted(report?.areAllPermissionsGranted())
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            })
            .onSameThread()
            .check()
    }

    fun checkCameraPermission(activity: Activity, callback: Callback) {
        Dexter.withActivity(activity)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    callback.isPermissionAccepted(true)
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    callback.isPermissionAccepted(false)
                }
            })
            .onSameThread()
            .check()
    }


    fun goToImageSettings(context: Context, message: String? = null) {
        val builder = AlertDialog.Builder(context)
            .setMessage(message ?: context.getString(R.string.upload_image_permission))
            .setPositiveButton(context.getString(R.string.settings)) { _, _ ->
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", context.packageName, null)
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
            .setNegativeButton(context.getString(R.string.cancel)) { dialogInterface, _ -> dialogInterface.dismiss() }
        builder.show()
    }


    fun goToLocationPermissionSettings(context: Context?, message: String? = null) {
        context?.let { context ->
            val builder = AlertDialog.Builder(context)
                .setMessage(message ?: context.getString(R.string.access_location_permission))
                .setPositiveButton(context.getString(R.string.settings)) { _, _ ->
                    val intent = Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", context.packageName, null)
                    )
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
                .setNegativeButton(context.getString(R.string.cancel)) { dialogInterface, _ -> dialogInterface.dismiss() }
            builder.show()
        }
    }


    fun isLocationServiceEnabled(context: Context): Boolean {
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        return gpsEnabled || networkEnabled
    }

    fun isInFlightMode(appContext: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.System.getInt(
                appContext.contentResolver,
                Settings.System.AIRPLANE_MODE_ON,
                0
            ) != 0

        } else {
            return Settings.Global.getInt(
                appContext.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON,
                0
            ) != 0
        }
    }


}