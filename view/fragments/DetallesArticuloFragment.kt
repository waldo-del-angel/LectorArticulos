package com.limonalmacenes.view.fragments

import android.os.Bundle
import android.support.design.chip.Chip
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.limonalmacenes.R
import kotlinx.android.synthetic.main.fragment_articulo_encontrado.*

class DetallesArticuloFragment : Fragment() {
    private lateinit var listaArticulos: ArrayList<MutableList<Any>>
    private var seleccionArticulo = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        listaArticulos = arguments?.getSerializable("lista") as ArrayList<MutableList<Any>>
        return inflater.inflate(R.layout.fragment_articulo_encontrado, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mostrarArticulo(listaArticulos)
        // Crear chips para las presentaciones
        for (i in 0 until listaArticulos.size) {
            val chip_formato = Chip(context)
            chip_formato.apply {
                id = i
                setChipBackgroundColorResource(R.color.chip_group_background)
                text = listaArticulos[i][4].toString()
                isCheckable = true
                isClickable = true
                animate()
            }
            chigroup_formato.addView(chip_formato)
        }

        // Eventos del ChipGroup para seleccionar la presentacion del producto
        chigroup_formato.setOnCheckedChangeListener { chipGroup, _ ->
            seleccionArticulo = chipGroup.checkedChipId.takeIf { it > 0 } ?: 0
            mostrarArticulo(listaArticulos)
        }
    }

    fun mostrarArticulo(listaArticulos: ArrayList<MutableList<Any>>) {
        detalles_tv_identificacion.text = listaArticulos[seleccionArticulo][1].toString()
        detalles_tv_codbarras.text = listaArticulos[seleccionArticulo][2].toString()
        detalles_tv_descripcion.text = listaArticulos[seleccionArticulo][3].toString()
        detalles_tv_presentacion.text = listaArticulos[seleccionArticulo][5].toString()
        detalles_tv_piezascaja.text = listaArticulos[seleccionArticulo][6].toString()
        detalles_tv_familia.text = listaArticulos[seleccionArticulo][7].toString()
        detalles_tv_subfamilia.text = listaArticulos[seleccionArticulo][8].toString()
        detalles_tv_marca.text = listaArticulos[seleccionArticulo][9].toString()
        detalles_tv_precio_con_iva.text = "$${String.format("%.2f", listaArticulos[seleccionArticulo][12])}"
    }

}