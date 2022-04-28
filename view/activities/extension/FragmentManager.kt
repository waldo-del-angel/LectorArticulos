package com.limonalmacenes.view.activities.extension

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.limonalmacenes.R

fun FragmentManager.detach() {
    findFragmentById(R.id.contenedor_fragments)?.also { beginTransaction().detach(it).commit() }
}

fun FragmentManager.attach(fragment: Fragment, tag: String) {
    if (fragment.isDetached)
        beginTransaction().attach(fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit()
    else
        beginTransaction().add(R.id.contenedor_fragments, fragment, tag).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit()
}