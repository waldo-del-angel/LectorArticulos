package com.limonalmacenes.view.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.limonalmacenes.R
import com.limonalmacenes.view.activities.ListaArticulos
import kotlinx.android.synthetic.main.fragment_descripcion.*

class DescripcionFragment: Fragment(){

    companion object {
        val TAG: String = DescripcionFragment::class.java.simpleName
        fun newInstance() = DescripcionFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_descripcion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmDesc_btnBuscar.setOnClickListener { buscar() }

        fragmDesc_etBusqueda.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    buscar()
                    true
                }
                else -> false
            }
        }
    }

    private fun buscar() {
        fragmDesc_etBusqueda.validate("Campo vacío") { s ->
            s.isValid().also {
                if(it){ irActivityLista() }
            }
        }
    }

    fun String.isValid(): Boolean = this.isNotEmpty()

    fun EditText.validate(message: String, validator: (String) -> Boolean) {
        this.error = if (validator(this.text.toString())) null else message
    }

    fun irActivityLista() {
        Intent(context, ListaArticulos::class.java).apply {
            putExtra("BUSQUEDA", fragmDesc_etBusqueda.text.toString())
            putExtra("METODO_BUSQUEDA", 3)
        }.also { startActivity(it) }
    }
}