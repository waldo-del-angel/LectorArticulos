package com.limonalmacenes.view.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.chip.Chip
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import com.limonalmacenes.R
import com.limonalmacenes.interactor.database.QueryInteractor
import com.limonalmacenes.interfaces.query.QueryView
import com.limonalmacenes.presenter.QueryDetallesPresenter
import com.limonalmacenes.view.fragments.DetallesArticuloFragment
import com.limonalmacenes.view.fragments.ServerErrorFragment
import com.limonalmacenes.view.fragments.SinResultadosFragment
import kotlinx.android.synthetic.main.activity_detalles_articulo.*

class DetallesArticulo : AppCompatActivity() , QueryView {

    private var busqueda: String? = null
    private var tipo_busqueda: Int? = null

    private lateinit var queryPresenter: QueryDetallesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_articulo)
        setSupportActionBar(detalles_toolbar)

        queryPresenter = QueryDetallesPresenter(this, QueryInteractor())

        busqueda = intent?.extras?.getString("BUSQUEDA")
        tipo_busqueda = intent?.extras?.getInt("METODO_BUSQUEDA")
        queryPresenter.getQuery(busqueda?:"", tipo_busqueda ?:2)

    }

    override fun onDestroy() {
        super.onDestroy()
        queryPresenter.OnDestroy()
    }

    override fun showResultado(result: ArrayList<MutableList<Any>>) {
        val bundle = Bundle()
        bundle.putSerializable("lista", result)

        val manager = supportFragmentManager
        val ft = manager.beginTransaction()

        val fragmentDetalles = DetallesArticuloFragment()
        fragmentDetalles.arguments = bundle

        ft.replace(R.id.act_detalles_frame, fragmentDetalles, "DETALLES_ARTICULO_FRAGMENT")
        ft.commitAllowingStateLoss()
    }

    override fun showLoadingElement() {
        act_detalles_progressbar.visibility = View.VISIBLE
    }

    override fun hideLoadingElement() {
        act_detalles_progressbar.visibility = View.GONE
    }

    override fun showServerError() {
        addFragment(ServerErrorFragment(), "SERVER_ERROR")
    }

    override fun showResultNotFound() {
        addFragment(SinResultadosFragment(), "SIN_RESULTADOS")
    }

    fun addFragment(fragment: Fragment, tag: String) {
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()
        ft.replace(R.id.act_detalles_frame, fragment, tag)
        ft.commitAllowingStateLoss()
    }
}
