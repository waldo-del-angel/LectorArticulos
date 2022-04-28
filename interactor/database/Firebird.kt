package com.limonalmacenes.interactor.database

import android.util.Log
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object Firebird {
    private var conexion: Connection? = null

    private val url_developer = "jdbc:firebirdsql://192.168.1.71:3050//home/waldo/Datos/EMPRE001.GDB"
    private val url_super_tantoyuca = "jdbc:firebirdsql://192.168.1.5:3050/C:\\Archivos de Programa\\Techni-web\\Gestion\\Datos\\EMPRE005.GDB"

    private lateinit var server: String
    private val map = mapOf("user" to "SYSDBA", "password" to "tw2000", "encoding" to "ISO8859_1", "autoReconnect" to "true", "interactiveClient" to "true")
    private val props = map.toProperties()
    private var status = false

    private lateinit var ssid: String

    @Synchronized
    fun setSSID(ssid: String?) {
        this.ssid = ssid ?: "RED_DESCONOCIDA"
        establecerUrlBD()
    }

    @Synchronized
    fun establecerUrlBD() {
        when(ssid){
            "\"INFINITUM3769_2.4\"" -> server = url_developer
            "\"waldo\"" -> server = url_developer
            "<unknown ssid>" -> server = url_developer
            "\"SUPER_TANTOYUCA\"" -> server = url_super_tantoyuca
        }
    }

    @Synchronized
    fun connection() : Connection? {
        if(estadoConexion()){
            return conexion
        } else {
            if (reconectar()) {
                return conexion
            } else {
                return null
            }
        }
    }

    @Synchronized
    fun estadoConexion() : Boolean {
        try {
            return conexion!!.isValid(0)
        } catch (e: Exception){
            return false
        }
    }

    @Synchronized
    fun reconectar() : Boolean {
        if (status) {
            if (conectar()) {
                status = true
                return status
            } else {
                status = false
                return status
            }
        } else {
            return false
        }
    }

    @Synchronized
    fun conectar() : Boolean {
        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver").kotlin
            try {
                conexion = DriverManager.getConnection(server, props)
                status = true
                return status
            } catch (e: SQLException) {
                Log.e("DBFIREBIRD:", "ERROR AL CONECTAR CON LA BASE DE DATOS")
                Log.e("DBFIREBIRD:", e.localizedMessage)
                status = false
                return status
            }
        } catch (v: ClassNotFoundException) {
            Log.e("DBFIREBIRD:", "ERROR AL REGISTRAR EL DRIVER")
            status = false
            return status
        }
    }
}