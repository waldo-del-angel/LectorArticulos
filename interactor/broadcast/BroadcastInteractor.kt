package com.limonalmacenes.interactor.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.util.Log
import android.widget.Toast
import com.limonalmacenes.interactor.database.ConexionInteractor
import com.limonalmacenes.interfaces.broadcast.BroadcastInteractorListener

object BroadcastInteractor : BroadcastReceiver() {

    private val redes = arrayOf("\"waldo\"","<unknown ssid>", "\"INFINITUM3769_2.4\"", "\"SUPER_MATRIZ\"", "\"SUPER_TANTOYUCA\"", "\"SUPER_TEMPOAL\"", "\"SUPER_HIDALGO\"", "\"SUPER_GLORIA\"", "\"SUPER_MERCADO\"")
    private lateinit var broadcastInteractorListener: BroadcastInteractorListener

    fun conexionPresenter(broadcastInteractorListener: BroadcastInteractorListener) {
        BroadcastInteractor.broadcastInteractorListener = broadcastInteractorListener
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val extra = intent?.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 4)
        when (extra) {
            WifiManager.WIFI_STATE_ENABLED -> {
                val mainWifi: WifiManager? = context?.applicationContext?.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val currentWifi: WifiInfo? = mainWifi?.connectionInfo
                if(redes.contains(currentWifi?.ssid)) {
                    broadcastInteractorListener.conexionExitosa(currentWifi?.ssid)
                    conectarDB(ConexionInteractor(), currentWifi?.ssid)
                } else {
                    broadcastInteractorListener.conexionFallo()
                }
            }
            WifiManager.WIFI_STATE_DISABLED -> broadcastInteractorListener.conexionFallo()
        }
    }

    @Synchronized
    private fun conectarDB(conn: ConexionInteractor, ssid: String?) {
        conn.conexionPresenter(ssid, broadcastInteractorListener)
        conn.execute()
    }
}