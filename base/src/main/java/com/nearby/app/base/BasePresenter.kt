package com.nearby.app.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable


abstract class BasePresenter<View> : ViewModel(), LifecycleObserver, MvpPresenter<View> {

    protected var view: View? = null
    protected var viewLifecycle: Lifecycle? = null
    protected var compositeDisposable = CompositeDisposable()

    override fun attachView(view: View, viewLifecycle: Lifecycle) {
        this.view = view
        this.viewLifecycle = viewLifecycle
        if (compositeDisposable.isDisposed) {
            compositeDisposable = CompositeDisposable()
        }


        viewLifecycle.addObserver(this)
    }



    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected open fun onViewDestroyed() {
        view = null
        viewLifecycle = null
        compositeDisposable.dispose()

    }

    fun isViewAttached(): Boolean {
        return view != null
    }

}