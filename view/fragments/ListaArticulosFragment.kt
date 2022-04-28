package com.limonalmacenes.view.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.limonalmacenes.R
import com.limonalmacenes.view.activities.DetallesArticulo
import com.limonalmacenes.view.articulos.ArticulosAdapter
import kotlinx.android.synthetic.main.fragment_lista_articulos.*

class ListaArticulosFragment: Fragment() {

    private lateinit var listaArticulos: ArrayList<MutableList<Any>>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        listaArticulos = arguments?.getSerializable("lista") as ArrayList<MutableList<Any>>
        return inflater.inflate(R.layout.fragment_lista_articulos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuracionRecycler()
        initRecycler()
    }

    private fun configuracionRecycler(){
        frag_lista_recycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun initRecycler(){
        frag_lista_recycler.adapter = ArticulosAdapter(listaArticulos){
            Intent(context, DetallesArticulo::class.java).apply {
                putExtra("BUSQUEDA", it[0].toString())
                putExtra("METODO_BUSQUEDA", 2)
            }.also { startActivity(it) }
        }
    }
}