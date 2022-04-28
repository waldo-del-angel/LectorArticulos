package com.limonalmacenes.interfaces.query

interface QueryPresenterListener {
    fun getQuery(busqueda: String, tipo_busqueda: Int)
    fun setResultado(result: ArrayList<MutableList<Any>>)

    fun showLoadingElement()

    fun serverError()
    fun resultNotFound()
}