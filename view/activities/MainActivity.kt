package com.limonalmacenes.view.activities

import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.view.MenuItem
import android.widget.Toast
import com.limonalmacenes.R
import com.limonalmacenes.interactor.permisos.PermisosInteractor
import com.limonalmacenes.interfaces.permissions.PermisosView
import com.limonalmacenes.presenter.PermisosPresenter
import com.limonalmacenes.view.activities.helper.*
import com.limonalmacenes.view.activities.extension.*
import com.limonalmacenes.view.BroadcastViewImpl
import com.limonalmacenes.view.fragments.CodigoBarrasFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.camara_permiso_denegado.*

class MainActivity : BroadcastViewImpl(), PermisosView , BottomNavigationView.OnNavigationItemSelectedListener{

    private val KEY_POSITION = "keyPosition"
    private var navPosition: BottomNavigationPosition = BottomNavigationPosition.CODIGOBARRAS

    private val permisosPresenter = PermisosPresenter(this, PermisosInteractor())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBottomNavigation()
        initFragment(savedInstanceState)
        // Solicitud de permisos
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) permisosPresenter.verificarPermisos(this)
    }

    override fun onDestroy() {
        permisosPresenter.onDestroy()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        if(navPosition.id == 0) {
            recargarFragment()
        }
    }

    // ----- Metodos relacionados a la creacion de fragments ----- //

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(KEY_POSITION, navPosition.id)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.also {
            val id = it.getInt(KEY_POSITION, BottomNavigationPosition.CODIGOBARRAS.id)
            navPosition = findNavigationPositionById(id)
        }
    }

    private fun initBottomNavigation() {
        bottomNavigation.active(navPosition.position)
        bottomNavigation.setOnNavigationItemSelectedListener(this)
    }

    private fun initFragment(savedInstanceState: Bundle?) {
        savedInstanceState ?: switchFragment(BottomNavigationPosition.CODIGOBARRAS)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navPosition = findNavigationPositionById(item.itemId)
        return switchFragment(navPosition)
    }

    private fun switchFragment(navPosition: BottomNavigationPosition): Boolean {
        return supportFragmentManager.findFragment(navPosition).let {
            if (it.isAdded) return false
            supportFragmentManager.detach()
            supportFragmentManager.attach(it, navPosition.getTag())
            supportFragmentManager.executePendingTransactions()
        }
    }

    private fun FragmentManager.findFragment(position: BottomNavigationPosition): Fragment =
        findFragmentByTag(position.getTag()) ?: position.createFragment()

    // ----- Metodos relacionados a la peticion de permisos ----- //

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permisosPresenter.comprobarPermisoConcedido(requestCode, grantResults)
    }

    override fun mostrarPermisoAceptado() {
        Toast.makeText(this, "En hora buena!", Toast.LENGTH_LONG).show()
    }

    override fun mostrarPermisoDenegado() {
        Toast.makeText(this, "Permisos denegados \uD83D\uDE1E", Toast.LENGTH_LONG).show()
    }

    override fun mostrarJustificacion() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Se requiere permiso para acceder a la cámara para que esta aplicación escanee el código de barras del producto.").setTitle("Permiso requerido")
        builder.setPositiveButton("CONTINUAR") { _, _  -> realizarSolicitud() }
        val dialog = builder.create()
        dialog.show()
    }

    override fun recargarFragment() {
        var fr = supportFragmentManager?.findFragmentByTag(CodigoBarrasFragment::class.java.simpleName)
        var ft = supportFragmentManager?.beginTransaction()
        fr?.let { ft?.detach(it) }
        fr?.let { ft?.attach(it) }
        ft?.commit()
    }

    override fun realizarSolicitud() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 100)
    }
}