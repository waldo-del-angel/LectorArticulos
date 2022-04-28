package com.limonalmacenes.interfaces.permissions

interface PermisosListener {
    fun permisoAceptado()
    fun permisoDenegado() : Unit?
    fun justificacion() : Unit?
    fun realizarSolicitud() : Unit?
}