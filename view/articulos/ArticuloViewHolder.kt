package com.limonalmacenes.view.articulos

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_articulo.view.*

class ArticuloViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun mostrarArticulo(articulo: MutableList<Any>, listener: (MutableList<Any>) -> Unit) = with(itemView) {
        itemDescripcion.text = articulo[2].toString() // DESCRIPCION
        itemPresentacion.text = articulo[3].toString() // PRESENTACION
        itemCodBarras.text = articulo[1].toString() // CODIGO_BARRAS
        setOnClickListener { listener(articulo) }
    }

}