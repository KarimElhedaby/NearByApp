package com.nearby.app.utils


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.nearby.app.utils.R


fun View.showSnackbar(
    @SuppressLint("SupportAnnotationUsage") @StringRes snackbarText: String, timeLength: Int
) {
    Snackbar.make(this, snackbarText, timeLength).show()
}

fun androidx.constraintlayout.widget.Group.setAllOnClickListener(listener: View.OnClickListener?) {
    referencedIds.forEach { id ->
        rootView.findViewById<View>(id).setOnClickListener(listener)
    }
}

fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) {
    }
    return false
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}



fun ImageView?.loadImage(src: Any?, corner: Int? = null) {
    this?.context?.let {
        Glide.with(it)
            .load(src)
//            .apply {
//                corner?.let {
//                    this.transform(CenterCrop(), RoundedCorners(corner))
//                } ?: this.transform(CenterCrop(), CircleCrop())
//            }
            .into(this)
    }
}


fun ImageView?.loadImage(url: String?) {
    this?.context?.let {
        Glide.with(it)
            .load(url)
            .placeholder(R.drawable.ic_place_holder2)
            .into(this)
    }
}



fun ImageView?.loadImage(bitmap: Bitmap?) {
    this?.context?.let {
        Glide.with(it)
            .load(bitmap)
            .into(this)
    }

}



fun ImageView?.loadImage(uri: Drawable?) {
    this?.context?.let {
        Glide.with(it)
            .load(uri)
            .into(this)
    }

}

fun ImageView?.loadImage(src: Int?) {
    this?.context?.let {
        Glide.with(it)
            .load(src)
            .into(this)
    }
}

fun EditText.focus() {
    requestFocus()
    setSelection(length())
}

