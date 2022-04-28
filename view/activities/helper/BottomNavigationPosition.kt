package com.limonalmacenes.view.activities.helper

import android.support.v4.app.Fragment
import com.limonalmacenes.R
import com.limonalmacenes.view.fragments.CodigoBarrasFragment
import com.limonalmacenes.view.fragments.DescripcionFragment
import com.limonalmacenes.view.fragments.IdentificacionFragment

enum class BottomNavigationPosition(val position: Int, val id: Int) {
    CODIGOBARRAS(0, R.id.action_escaner),
    IDENTIFICACION(1, R.id.action_identificacion),
    DESCRIPCION(2, R.id.action_descripcion);
}

fun findNavigationPositionById(id: Int): BottomNavigationPosition = when(id) {
    BottomNavigationPosition.CODIGOBARRAS.id -> BottomNavigationPosition.CODIGOBARRAS
    BottomNavigationPosition.IDENTIFICACION.id -> BottomNavigationPosition.IDENTIFICACION
    BottomNavigationPosition.DESCRIPCION.id -> BottomNavigationPosition.DESCRIPCION
    else -> BottomNavigationPosition.CODIGOBARRAS
}

fun BottomNavigationPosition.createFragment(): Fragment = when (this) {
    BottomNavigationPosition.CODIGOBARRAS -> CodigoBarrasFragment.newInstance()
    BottomNavigationPosition.IDENTIFICACION -> IdentificacionFragment.newInstance()
    BottomNavigationPosition.DESCRIPCION -> DescripcionFragment.newInstance()
}

fun BottomNavigationPosition.getTag() : String = when (this) {
    BottomNavigationPosition.CODIGOBARRAS -> CodigoBarrasFragment.TAG
    BottomNavigationPosition.IDENTIFICACION -> IdentificacionFragment.TAG
    BottomNavigationPosition.DESCRIPCION -> DescripcionFragment.TAG
}