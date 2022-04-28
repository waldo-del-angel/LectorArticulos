package com.limonalmacenes.view

import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.limonalmacenes.R
import com.limonalmacenes.interactor.broadcast.BroadcastInteractor
import com.limonalmacenes.interfaces.broadcast.BroadcastView
import com.limonalmacenes.presenter.BroadcastPresenterImpl

open class BroadcastViewImpl : AppCompatActivity(), BroadcastView {

    private val msjConexionExitosa = "CONECTADO"
    private val msjConexionNoDisponible = "NO HAY CONEXION"

    private val broadcastPresenter = BroadcastPresenterImpl(this, BroadcastInteractor)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        broadcastPresenter.conexionInteractor()
        registerReceiver(BroadcastInteractor, IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION))
    }

    override fun onDestroy() {
        broadcastPresenter.onDestroy()
        super.onDestroy()
        unregisterReceiver(BroadcastInteractor)
    }

    override fun mostrarConexionExitosa(ssid: String?) {
//        var snackbar: Snackbar = Snackbar.make(
//            findViewById(R.id.coordinator),
//            msjConexionExitosa,
//            Snackbar.LENGTH_LONG)
//        snackbar.show()
        Toast.makeText(this, msjConexionExitosa, Toast.LENGTH_LONG).show()
    }

    @Synchronized
    override fun mostrarConexionNoDisponible() {
        var snackbar: Snackbar = Snackbar.make(
            findViewById(R.id.coordinator),
            msjConexionNoDisponible,
            Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    override fun mostrarConexionDBExitosa() {
//        var snackbar: Snackbar = Snackbar.make(
//            findViewById(R.id.coordinator),
//            "Conectado a Firebird",
//            Snackbar.LENGTH_LONG)
//        snackbar.show()
        Toast.makeText(this, "Conectado a Firebird", Toast.LENGTH_LONG).show()
    }

    override fun mostrarConexionDBRechazada() {
        Toast.makeText(this, "Conexión a Firebird falló", Toast.LENGTH_SHORT).show()
    }
}