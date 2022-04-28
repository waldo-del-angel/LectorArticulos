package com.limonalmacenes.presenter

import com.limonalmacenes.interactor.database.QueryInteractor
import com.limonalmacenes.interfaces.query.QueryPresenterListener
import com.limonalmacenes.view.activities.ListaArticulos

class QueryPresenter (private var listaArticulosView: ListaArticulos?, private val queryInteractor: QueryInteractor): QueryPresenterListener {

    override fun getQuery(busqueda: String, tipo_busqueda: Int) {
        queryInteractor.conexionPresenter(this, busqueda, tipo_busqueda)
        queryInteractor.execute()
    }

    override fun setResultado(result: ArrayList<MutableList<Any>>) {
        listaArticulosView?.apply {
            hideLoadingElement()
            showResultado(result)
        }
    }

    override fun resultNotFound() {
        listaArticulosView?.apply {
            hideLoadingElement()
            showResultNotFound()
        }
    }

    override fun serverError() {
        listaArticulosView?.apply {
            hideLoadingElement()
            showServerError()
        }
    }

    override fun showLoadingElement(){
        listaArticulosView?.showLoadingElement()
    }

    fun onDestroy() {
        listaArticulosView = null
    }
}