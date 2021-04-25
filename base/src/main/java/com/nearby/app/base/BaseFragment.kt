package com.nearby.app.base

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.transition.Transition
import com.nearby.app.Common


abstract class BaseFragment<P : MvpPresenter<*>> : Fragment(), MvpView {


    /***
     * abstract function to ensure developer to pass required presenter
     *
     *
     * @return Presenter
     *
     * */
    protected abstract val presenter: P

    lateinit var title: String

    /***
     *  abstract functions to initialize layout
     *  @param savedInstanceState
     *  @param view
     *
     *  @return void
     */
    protected abstract fun initLayout(savedInstanceState: Bundle?, view: View)


    /***
     * abstract function to get layout resource id
     *
     * @return Int
     *
     * */
    protected abstract fun getLayoutResource(): Int


    protected open fun hideKeyboardWhenTouchOutside(): Boolean = true

    private var mAlertDialog: AlertDialog? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutResource(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout(savedInstanceState, view)
        if (hideKeyboardWhenTouchOutside()) {
            keyboardDismissOnTouchOutside(view)
        }
    }

    override fun showLoading() {
        hideLoading()
        mAlertDialog = Common.showLoadingDialog(requireContext())
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
        Common.showSnackBar(requireActivity(), message)
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(resId: Int) {
        showMessage(getString(resId))
    }


    override fun hideKeyboard() {
        val inputMethodManager = requireActivity().getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager?
        inputMethodManager?.hideSoftInputFromWindow(
            requireActivity().currentFocus?.windowToken, 0
        )
    }

    private fun keyboardDismissOnTouchOutside(view: View) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener { _, _ ->
                hideKeyboard()
                false
            }
        }

        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                keyboardDismissOnTouchOutside(innerView)
            }
        }
    }

    override fun onNetworkError() {
        Toast.makeText(
            requireContext(),
            getString(R.string.server_connect_error),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


}



