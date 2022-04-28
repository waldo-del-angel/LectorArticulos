package com.limonalmacenes.interactor.codigobarras

import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.limonalmacenes.interfaces.camara.CodigoBarrasInteractorListener

class CodigoBarrasInteractor : BarcodeCallback {

    private lateinit var codBarrasListener: CodigoBarrasInteractorListener

    fun conexionPresenter(codBarrasListener: CodigoBarrasInteractorListener) {
        this.codBarrasListener = codBarrasListener
    }

    fun getCallback() : BarcodeCallback = this

    override fun barcodeResult(result: BarcodeResult?) = codBarrasListener.activityDetalles(result?.text ?: "")

    override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {}

}