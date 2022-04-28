package com.limonalmacenes.presenter

import com.journeyapps.barcodescanner.BarcodeCallback
import com.limonalmacenes.interactor.codigobarras.CodigoBarrasInteractor
import com.limonalmacenes.interfaces.camara.CodigoBarrasInteractorListener
import com.limonalmacenes.view.fragments.CodigoBarrasFragment

class CodigoBarrasPresenter(
    private var codBarrasFragment: CodigoBarrasFragment?,
    private val codBarrasInteractor: CodigoBarrasInteractor
) : CodigoBarrasInteractorListener {

    fun iniciarBarcodeCallback() {
        codBarrasInteractor.conexionPresenter(this)
    }

    fun getBarcodeCallback(): BarcodeCallback = codBarrasInteractor.getCallback()

    override fun activityDetalles(codigobarras: String) {
        codBarrasFragment?.apply {
            irActivityDetalles(codigobarras)
            mostrarEfectosArticuloEncotrado()
        }
    }

    fun onDestroy() {
        codBarrasFragment = null
    }
}