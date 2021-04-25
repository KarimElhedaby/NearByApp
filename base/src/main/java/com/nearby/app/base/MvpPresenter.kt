package com.nearby.app.base

import androidx.lifecycle.Lifecycle

interface MvpPresenter<View> {
    fun attachView(view: View, viewLifecycle: Lifecycle)
}