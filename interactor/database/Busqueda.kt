package com.limonalmacenes.interactor.database

import java.sql.PreparedStatement
import java.sql.ResultSet

object Busqueda {

    val listaArticulos = ArrayList<MutableList<Any>>()
    fun buscar(query: String): ArrayList<MutableList<Any>> {
        var ps: PreparedStatement? = Firebird.connection()?.prepareStatement(query)
        var rs: ResultSet? = ps?.executeQuery()
        if (rs != null) {
            while (rs.next()) {
                val articulo = mutableListOf<Any>()
                for (i in 1..rs.metaData.columnCount)
                    articulo.add(rs.getObject(i))
                listaArticulos.add(articulo)
            }
            ps?.close()
            return listaArticulos
        } else {
            ps?.close()
            return listaArticulos
        }
    }
}