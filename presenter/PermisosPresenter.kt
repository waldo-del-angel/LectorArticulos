package com.limonalmacenes.presenter

import android.app.Activity
import com.limonalmacenes.interactor.permisos.PermisosInteractor
import com.limonalmacenes.interfaces.permissions.PermisosListener
import com.limonalmacenes.interfaces.permissions.PermisosView

class PermisosPresenter(private var permisosView: PermisosView?, private val permisosInteractor: PermisosInteractor): PermisosListener{

    fun verificarPermisos(activity: Activity) {
        permisosInteractor.conexionPresenter(this)
        permisosInteractor.solicitarPermisos(activity)
    }

    fun comprobarPermisoConcedido(requestCode: Int, grantResults: IntArray) {
        permisosInteractor.verificarPermisos(requestCode, grantResults)
    }

    override fun permisoAceptado() {
        permisosView?.apply {
            mostrarPermisoAceptado()
            recargarFragment()
        }
    }

    override fun permisoDenegado() =
        permisosView?.mostrarPermisoDenegado()

    override fun justificacion() =
        permisosView?.mostrarJustificacion()

    override fun realizarSolicitud() =
        permisosView?.realizarSolicitud()

    fun onDestroy() {
        permisosView = null
    }
}