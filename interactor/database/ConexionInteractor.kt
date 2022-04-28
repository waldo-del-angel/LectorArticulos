package com.limonalmacenes.interactor.database

import android.os.AsyncTask
import com.limonalmacenes.interfaces.broadcast.BroadcastInteractorListener

class ConexionInteractor : AsyncTask<Void, Int, Int>() {

    private lateinit var databaseInteractorListener: BroadcastInteractorListener
    private var ssid: String? = null

    fun conexionPresenter(ssid: String?, databaseInteractorListener: BroadcastInteractorListener) {
        this.databaseInteractorListener = databaseInteractorListener
        this.ssid = ssid
    }

    override fun doInBackground(vararg params: Void?): Int {
        Thread.sleep(3_000)
        Firebird.setSSID(ssid)
        Firebird.conectar()
        return if (Firebird.connection() == null) 0 else 1
    }

    override fun onPostExecute(result: Int?) {
        super.onPostExecute(result)
        if (result == 1) {
            databaseInteractorListener.conexionDBExitosa()
        } else {
            databaseInteractorListener.conexionDBRechazada()
        }
    }
}