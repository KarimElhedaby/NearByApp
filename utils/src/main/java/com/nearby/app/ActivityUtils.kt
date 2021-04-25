package com.nearby.app

import android.os.Build
import android.transition.Slide
import android.view.Gravity
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.nearby.app.utils.R


fun replaceFragmentToActivity(
    supportFragmentManager: FragmentManager,
    fragment: Fragment,
    isSaved: Boolean = false,
    transitionView: View? = null
) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(R.id.container, fragment)
    if (isSaved) transaction.addToBackStack(null)
    transitionView?.let {
        transaction.addSharedElement(it, ViewCompat.getTransitionName(it)!!)
    }
    transaction.commit()
}

fun replaceFragment(
    childFragmentManager: FragmentManager,
    fragment: Fragment,
    isSaved: Boolean = false , sub_container_id:Int
) {
    val transaction = childFragmentManager.beginTransaction()
    transaction.replace(sub_container_id, fragment)
    if (isSaved) transaction.addToBackStack(null)
    transaction.commit()
}



fun getReplaceFragmentTransaction(
    supportFragmentManager: FragmentManager,
    fragment: Fragment,
    save: Boolean = false,
    container: Int = R.id.container
): FragmentTransaction {
    return supportFragmentManager.beginTransaction().replace(container, fragment).apply {
        if (save) addToBackStack(null)
    }
}




fun getAddFragmentTransaction(
    supportFragmentManager: FragmentManager,
    fragment: Fragment,
    save: Boolean = false,
    container: Int = R.id.container
): FragmentTransaction {
    return supportFragmentManager.beginTransaction().add(container, fragment).apply {
        if (save) addToBackStack(null)
    }
}

fun openHorizontally(
    supportFragmentManager: FragmentManager,
    fragment: Fragment,
    isSaved: Boolean = true


) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        fragment.enterTransition = Slide(Gravity.END).setDuration(400)
        fragment.exitTransition = Slide(Gravity.START).setDuration(400)
    }
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(R.id.container, fragment)
    if (isSaved) transaction.addToBackStack(null)
    transaction
        .commit()
}

fun replaceFragmentToActivityWithSharedElementAnimation(
    supportFragmentManager: FragmentManager,
    fragment: Fragment,
    isSaved: Boolean = false,
    transitionView: View
) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(R.id.container, fragment)
    transaction.setReorderingAllowed(true)
    transaction.addSharedElement(transitionView, ViewCompat.getTransitionName(transitionView)!!)
    if (isSaved) transaction.addToBackStack(null)
    transaction.commit()
}

