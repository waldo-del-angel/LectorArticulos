package com.limonalmacenes.view.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.zxing.client.android.BeepManager
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.limonalmacenes.R
import com.limonalmacenes.interactor.codigobarras.CodigoBarrasInteractor
import com.limonalmacenes.interfaces.camara.CodigoBarrasView
import com.limonalmacenes.presenter.CodigoBarrasPresenter
import com.limonalmacenes.view.activities.DetallesArticulo
import kotlinx.android.synthetic.main.camara_permiso_denegado.*

class CodigoBarrasFragment : Fragment(), CodigoBarrasView{

    companion object {
        val TAG: String = CodigoBarrasFragment::class.java.simpleName
        fun newInstance() = CodigoBarrasFragment()
    }

    private lateinit var vista: View
    private var codBarrasPresenter = CodigoBarrasPresenter(this, CodigoBarrasInteractor())
    private var barcodeview: DecoratedBarcodeView? = null
    private lateinit var callback: BarcodeCallback
    private lateinit var beepManager : BeepManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val permission = ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.CAMERA)
        if(permission == PackageManager.PERMISSION_GRANTED) {
            vista = inflater.inflate(R.layout.fragment_codigo_barras, container, false)
            codBarrasPresenter.iniciarBarcodeCallback()
            barcodeview = vista.findViewById(R.id.zxing_barcode_scanner) as DecoratedBarcodeView
            callback = codBarrasPresenter.getBarcodeCallback()
            beepManager = BeepManager(activity)
            beepManager.apply {
                isBeepEnabled = true
                isVibrateEnabled = true
            }
            barcodeview?.decodeContinuous(callback)
        } else {
            vista = inflater.inflate(R.layout.camara_permiso_denegado, container, false)
        }
        return vista
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val permission = ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.CAMERA)
        if(permission != PackageManager.PERMISSION_GRANTED) {
            btnConfiguracion.setOnClickListener { permisoManual() }
        }
    }

    override fun irActivityDetalles(codigobarras: String) {
        Intent(context, DetallesArticulo::class.java).apply {
            putExtra("BUSQUEDA", codigobarras)
            putExtra("METODO_BUSQUEDA", 1)
        }.also { startActivity(it) }
    }

    override fun onPause() {
        super.onPause()
        barcodeview?.pauseAndWait()
    }

    override fun onResume() {
        super.onResume()
        barcodeview?.resume()
    }

    override fun onDestroy() {
        codBarrasPresenter.onDestroy()
        super.onDestroy()
    }

    fun mostrarEfectosArticuloEncotrado() = beepManager.playBeepSoundAndVibrate()

    private fun permisoManual() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri: Uri = Uri.fromParts("package", activity?.packageName , null)
        intent.data = uri
        startActivity(intent)
    }
}
