package com.limonalmacenes.presenter

import com.limonalmacenes.interactor.database.ConexionInteractor
import com.limonalmacenes.interfaces.database.DatabaseInteractorListener
import com.limonalmacenes.interfaces.database.DatabaseView

class ConexionPresenter(private var databaseView: DatabaseView?, private var conn: ConexionInteractor) : DatabaseInteractorListener {

    fun conectar(ssid: String?) {
//        conn.conexionPresenter(ssid,this)
//        conn.execute()
    }

    override fun conexionDBExitosa() {
        databaseView?.mostrarConexionDBExitosa()
    }

    override fun conexionDBRechazada() {
        databaseView?.mostrarConexionDBRechazada()
    }

    fun onDestroy() {
        databaseView = null
    }
}