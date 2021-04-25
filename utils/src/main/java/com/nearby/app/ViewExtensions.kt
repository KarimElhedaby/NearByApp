package com.nearby.app.utils

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.graphics.Paint
import android.os.Build
import android.text.Html
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

fun View.secretB() {
    if (!this.isGoneB()) {
        this.visibility = View.GONE
    }
}


fun View.showB() {
    if (!this.isVisibleB()) {
        this.visibility = View.VISIBLE
    }
}

fun View.hideB() {
    this.visibility = View.INVISIBLE
}


fun View.isVisibleB(): Boolean = visibility == View.VISIBLE

fun View.isGoneB(): Boolean = visibility == View.GONE

fun View.isInvisibleB(): Boolean = visibility == View.INVISIBLE


fun freezeB(progressBar: View, appCompatActivity: AppCompatActivity) {
    appCompatActivity.window.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
    progressBar.showB()
}

fun freezeB(appCompatActivity: AppCompatActivity) {

}

fun EditText.updateText(text: String?) {
    this.setText(text, TextView.BufferType.EDITABLE)
}


fun View.flash() {

    this.alpha = 1f
    this.scaleX = 1f
    this.scaleY = 1f

    val alpha = PropertyValuesHolder.ofFloat(View.ALPHA, 0f)
    val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, .95f)
    val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, .95f)

    val ass = ObjectAnimator.ofPropertyValuesHolder(this, alpha, scaleX, scaleY)
    ass.repeatCount = 3
    ass.duration = 200
    ass.repeatMode = ValueAnimator.REVERSE
    ass.start()

}

fun EditText.flash() {
    this.requestFocus()

    this.alpha = 1f
    this.scaleX = 1f
    this.scaleY = 1f

    val alpha = PropertyValuesHolder.ofFloat(View.ALPHA, 0f)
    val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, .95f)
    val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, .95f)

    val ass = ObjectAnimator.ofPropertyValuesHolder(this, alpha, scaleX, scaleY)
    ass.repeatCount = 3
    ass.duration = 200
    ass.repeatMode = ValueAnimator.REVERSE
    ass.start()
}

fun TextView.setHtmlText(text: String?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
    } else {
        this.text = Html.fromHtml(text)
    }
}

fun View.setDrawableBG(drawable: Int) {
    this.background = ContextCompat.getDrawable(context, drawable)
}

fun ImageView.setDrawableResource(drawable: Int) {
    this.setImageResource(drawable)
}

fun TextView.setButtonDrawableChooseCountry(drawable: Int) {
    this.setCompoundDrawablesWithIntrinsicBounds(
        drawable,
        0,
        0,
        0
    )
}

fun Button.setButtonDrawableCancelChooseCountry() {
    this.setCompoundDrawablesWithIntrinsicBounds(
        0,
        0,
        0,
        0
    )
}

fun View.setColorBG(color: Int) {
    this.setBackgroundResource(color)
}

fun Button.setButtonTextColor(color: Int) {
    this.setTextColor(ContextCompat.getColor(context, color))
}



fun TextView.setUnderLine() {
    this.paintFlags = this.paintFlags or Paint.UNDERLINE_TEXT_FLAG

}

fun Button.setUnderLine() {
    this.paintFlags = this.paintFlags or Paint.UNDERLINE_TEXT_FLAG

}