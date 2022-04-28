package com.limonalmacenes.presenter

import com.limonalmacenes.interactor.broadcast.BroadcastInteractor
import com.limonalmacenes.interfaces.broadcast.BroadcastInteractorListener
import com.limonalmacenes.interfaces.broadcast.BroadcastView

class BroadcastPresenterImpl(private var broadcastView: BroadcastView?, private val broadcastInteractor: BroadcastInteractor):
    BroadcastInteractorListener {

    fun conexionInteractor() {
        broadcastInteractor.conexionPresenter(this)
    }

    override fun conexionExitosa(ssid: String?) {
        broadcastView?.mostrarConexionExitosa(ssid)
    }

    override fun conexionFallo() {
        broadcastView?.mostrarConexionNoDisponible()
    }

    override fun conexionDBExitosa() {
        broadcastView?.mostrarConexionDBExitosa()
    }

    override fun conexionDBRechazada() {
        broadcastView?.mostrarConexionDBRechazada()
    }

    fun onDestroy() {
        broadcastView = null
    }
}