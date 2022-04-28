package com.limonalmacenes.interfaces.broadcast

interface BroadcastInteractorListener {
    fun conexionExitosa(ssid: String?)
    fun conexionFallo()
    fun conexionDBExitosa()
    fun conexionDBRechazada()
}