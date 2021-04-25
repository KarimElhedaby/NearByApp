package com.nearby.app

import android.app.Activity
import android.app.ActivityManager
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ParseException
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.LocaleList
import android.text.format.DateUtils
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import com.nearby.app.utils.R
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.net.NetworkInterface
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*


object Common {

    fun fullScreen(activity: Activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE)
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }


    var imagePath :File? = null

    fun saveBitmap(bitmap: Bitmap) {
        imagePath =
            File(Environment.getExternalStorageDirectory().toString() + "/scrnshot.png") ////File imagePath
        val fos: FileOutputStream
        try {
            fos = FileOutputStream(imagePath)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: FileNotFoundException) {
            Log.e("GREC", e.message, e)
        } catch (e: IOException) {
            Log.e("GREC", e.message, e)
        }
    }

    fun shareIt(context: Context) {
        val uri: Uri? =
            imagePath?.let {
                FileProvider.getUriForFile(
                   context,
                    "com.photoweather.app.provider", //(use your app signature + ".provider" )
                    it
                )
            }
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "image/*"

        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri)
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"))
    }





    fun changeLang(language: String, context: Context): Context {
        var mContext = context
        val res = mContext.resources
        val configuration = res.configuration
        val newLocale = Locale(language)

        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                configuration.setLocale(newLocale)
                val localeList = LocaleList(newLocale)
                LocaleList.setDefault(localeList)
                configuration.setLocales(localeList)
                mContext = mContext.createConfigurationContext(configuration)

            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 -> {
                configuration.setLocale(newLocale)
                mContext = mContext.createConfigurationContext(configuration)

            }
            else -> {
                configuration.locale = newLocale
                res.updateConfiguration(configuration, res.displayMetrics)
            }
        }
        return mContext
    }

    fun showLoadingDialog(context: Context): AlertDialog {
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null)
        val builder = AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
            .setView(mDialogView).show()
        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return builder
    }

    fun showSnackBar(activity: Activity, message: String) {
        val snackbar = Snackbar.make(
            activity.findViewById<View>(android.R.id.content),
            message, Snackbar.LENGTH_SHORT
        )
        snackbar.show()
    }


    fun arabicToDecimal(number: String): String {
        val chars = CharArray(number.length)
        for (i in 0 until number.length) {
            var ch = number[i]
            if (ch.toInt() in 0x0660..0x0669)
                ch -= (0x0660 - '0'.toInt())
            else if (ch.toInt() in 0x06f0..0x06F9)
                ch -= (0x06f0 - '0'.toInt())
            chars[i] = ch
        }
        return String(chars)
    }

    fun changeStatusBarColor(activity: Activity, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            activity.window.statusBarColor = activity.resources.getColor(color)
            activity.window.navigationBarColor = activity.resources.getColor(color)
        }
    }


    fun getDate(milliSeconds: Long): String? {
        val calender = Calendar.getInstance()
        calender.timeInMillis = milliSeconds
        val now = GregorianCalendar()
        val old = GregorianCalendar(
            calender.get(Calendar.YEAR),
            calender.get(Calendar.MONTH),
            calender.get(Calendar.DAY_OF_MONTH),
            calender.get(Calendar.HOUR_OF_DAY),
            calender.get(Calendar.MINUTE),
            calender.get(Calendar.SECOND)
        )

        return DateUtils.getRelativeTimeSpanString(
            old.timeInMillis,
            now.timeInMillis,
            DateUtils.SECOND_IN_MILLIS
        ).toString()
    }

    fun formatDateForChat(milliSeconds: Long): String {
        val currentDate = Date()
        val date = Date(milliSeconds)

        val timeFormatter = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        val dayMonthFormatter = SimpleDateFormat("dd MMM", Locale.ENGLISH)
        val yearFormatter = SimpleDateFormat("yyyy", Locale.ENGLISH)
        val dayMonthYearFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

        return if (yearFormatter.format(currentDate) == yearFormatter.format(date)) {
            if (dayMonthFormatter.format(date) == dayMonthFormatter.format(currentDate)) {
                timeFormatter.format(date)
            } else {
                dayMonthFormatter.format(date)
            }
        } else {
            dayMonthYearFormatter.format(date)
        }
    }
    @Throws(ParseException::class)
    fun getTimeStampFromDateTime(mDateTime: String, mDateFormat: String): Long {
        val dateFormat = SimpleDateFormat(mDateFormat)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = dateFormat.parse(mDateTime)
        return date.time
    }
    fun getTime(timeStamp: String?): String {
        if (timeStamp == null) {
            return "Now"
        } else {
            val serverFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            val data = serverFormat.parse(timeStamp)
            val timeZone = TimeZone.getDefault()
            val rowOffest = timeZone.rawOffset
            var local = 0
            if (data != null) {
                local = (data.time + rowOffest).toInt()
            }
            val calendar = GregorianCalendar()
            calendar.timeInMillis = local.toLong()
            val format = SimpleDateFormat("hh:mm a", Locale.ENGLISH)

            return format.format(calendar.time)
        }
    }

    fun getCurrentTime():Long{
        val tsLong = System.currentTimeMillis() / 1000
        return tsLong
    }
    fun getMacAddr(): String {
        try {
            val all = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (nif in all) {
                if (!nif.name.equals("wlan0", ignoreCase = true)) continue

                val macBytes = nif.hardwareAddress ?: return ""

                val res1 = StringBuilder()
                for (b in macBytes) {
                    //res1.append(Integer.toHexString(b & 0xFF) + ":");
                    res1.append(String.format("%02X:", b))
                }

                if (res1.isNotEmpty()) {
                    res1.deleteCharAt(res1.length - 1)
                }
                return res1.toString()
            }
        } catch (ex: Exception) {
        }

        return "02:00:00:00:00:00"
    }

    fun printKeyHash(activity: Activity) {
        // Add code to print out the key hash
        try {
            val info = activity.packageManager.getPackageInfo(
                activity.packageName,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e("KeyHash:", e.toString())
        } catch (e: NoSuchAlgorithmException) {
            Log.e("KeyHash:", e.toString())
        }

    }

    fun generateImageName(name: String): String {
        val ext = name.substringAfterLast(".")
        return "photo${generateUniqueId()}.$ext"
    }


    fun generateUniqueId(): Int {
        val id = UUID.randomUUID()
        val uid = id.hashCode()
        return ("" + uid).replace("-", "").toInt()
    }

    fun isForeground(context: Context): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningTaskInfo = manager.getRunningTasks(1)
        val componentInfo = runningTaskInfo[0].topActivity
        return componentInfo?.packageName == Constants.PACKAGE_NAME
    }

    fun isAboveLollipop(): Boolean {
        return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    }


}


