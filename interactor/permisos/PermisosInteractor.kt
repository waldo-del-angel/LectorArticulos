package com.limonalmacenes.interactor.permisos

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.limonalmacenes.interfaces.permissions.PermisosListener

class PermisosInteractor {

    lateinit var permisosPresenterListener: PermisosListener

    fun conexionPresenter(permisosPresenterListener: PermisosListener) {
        this.permisosPresenterListener = permisosPresenterListener
    }

    fun solicitarPermisos(activity: Activity) {
        val permission = ContextCompat.checkSelfPermission(activity.applicationContext, android.Manifest.permission.CAMERA)
        if(permission != PackageManager.PERMISSION_GRANTED) {
            // Mostrar Justificacion
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.CAMERA)) {
                permisosPresenterListener.justificacion()
            } else {
                permisosPresenterListener.realizarSolicitud()
            }
        }
    }

    fun verificarPermisos(requestCode: Int, grantResults: IntArray){
        when (requestCode) {
            100 -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    permisosPresenterListener.permisoDenegado()
                } else {
                    permisosPresenterListener.permisoAceptado()
                }
            }
        }
    }

}