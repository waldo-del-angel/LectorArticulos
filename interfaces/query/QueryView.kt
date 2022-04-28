package com.limonalmacenes.interfaces.query

interface QueryView {
    fun showResultado(result: ArrayList<MutableList<Any>>)
    fun showLoadingElement()
    fun hideLoadingElement()
    fun showServerError()
    fun showResultNotFound()
}