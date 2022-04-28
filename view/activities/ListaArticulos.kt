package com.limonalmacenes.view.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.limonalmacenes.R
import com.limonalmacenes.interactor.database.QueryInteractor
import com.limonalmacenes.interfaces.query.QueryView
import com.limonalmacenes.presenter.QueryPresenter
import com.limonalmacenes.view.fragments.ListaArticulosFragment
import com.limonalmacenes.view.fragments.ServerErrorFragment
import com.limonalmacenes.view.fragments.SinResultadosFragment
import kotlinx.android.synthetic.main.activity_lista_articulos.*

class ListaArticulos : AppCompatActivity() , QueryView {

    private var busqueda: String? = null
    private var tipo_busqueda: Int? = null

    private var queryPresenter = QueryPresenter(this, QueryInteractor())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_articulos)
        setSupportActionBar(act_lista_toolbar)

        intent?.extras?.apply {
            busqueda = getString("BUSQUEDA")
            tipo_busqueda = getInt("METODO_BUSQUEDA")
        }

        queryPresenter.getQuery(busqueda?:"", tipo_busqueda ?:3)
    }

    override fun onDestroy() {
        super.onDestroy()
        queryPresenter.onDestroy()
    }

    override fun showResultado(result: ArrayList<MutableList<Any>>) {
        val bundle = Bundle()
        bundle.putSerializable("lista", result)

        val manager = supportFragmentManager
        val ft = manager.beginTransaction()

        val fragmentLista = ListaArticulosFragment()
        fragmentLista.arguments = bundle

        ft.replace(R.id.act_lista_framelayout, fragmentLista, "LISTA_ARTICULOS_FRAGMENT")
        ft.commitAllowingStateLoss()
    }

    override fun showServerError() {
        addFragment(ServerErrorFragment(), "SERVER_ERROR")
    }

    override fun showResultNotFound() {
        addFragment(SinResultadosFragment(), "SIN_RESULTADOS")
    }

    override fun showLoadingElement() { progress_circular.visibility = View.VISIBLE }

    override fun hideLoadingElement() { progress_circular.visibility = View.GONE }

    fun addFragment(fragment: Fragment, tag: String) {
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()
        ft.replace(R.id.act_lista_framelayout, fragment, tag)
        ft.commitAllowingStateLoss()
    }
}
