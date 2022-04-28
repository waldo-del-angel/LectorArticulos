package com.limonalmacenes.view.articulos

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.limonalmacenes.R

class ArticulosAdapter (private var listaArticulos: ArrayList<MutableList<Any>>, private val listener: (MutableList<Any>) -> Unit) : RecyclerView.Adapter<ArticuloViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticuloViewHolder =
        ArticuloViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_articulo, parent, false))

    override fun getItemCount(): Int =
        listaArticulos.size

    override fun onBindViewHolder(holder: ArticuloViewHolder, position: Int) =
        holder.mostrarArticulo(listaArticulos[position], listener)

}