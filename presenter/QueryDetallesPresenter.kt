package com.limonalmacenes.presenter

import com.limonalmacenes.interactor.database.QueryInteractor
import com.limonalmacenes.interfaces.query.QueryPresenterListener
import com.limonalmacenes.view.activities.DetallesArticulo

class QueryDetallesPresenter (private var detallesView: DetallesArticulo?, private val queryInteractor: QueryInteractor) : QueryPresenterListener {
    override fun getQuery(busqueda: String, tipo_busqueda: Int) {
        queryInteractor.conexionPresenter(this, busqueda, tipo_busqueda)
        queryInteractor.execute()
    }

    override fun setResultado(result: ArrayList<MutableList<Any>>) {
        detallesView?.apply {
            hideLoadingElement()
            showResultado(result)
        }
    }

    override fun showLoadingElement() {
        detallesView?.showLoadingElement()
    }

    override fun serverError() {
        detallesView?.apply {
            hideLoadingElement()
            showServerError()
        }
    }

    override fun resultNotFound() {
        detallesView?.apply {
            hideLoadingElement()
            showResultNotFound()
        }
    }

    fun OnDestroy() {
        detallesView = null
    }
}