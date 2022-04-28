package com.limonalmacenes.interfaces.broadcast

/**
 * Quién:
 * - Actividad
 * - Fragmento
 * - Vista y su contrato, es decir, la interfaz.
 *
 * Propósito:
 * Hacer todas las cosas relacionadas con la interfaz de usuario y
 * todo lo que necesita el contexto de Android.
 *
 * Función:
 * Siempre que necesite realizar alguna acción,
 * debe activar la función respectiva del Presenter utilizando el objeto.
 * No debe tener ninguna lógica de negocio.
 */

interface BroadcastView {
    fun mostrarConexionExitosa(ssid: String?)
    fun mostrarConexionNoDisponible()
    fun mostrarConexionDBExitosa()
    fun mostrarConexionDBRechazada()
}