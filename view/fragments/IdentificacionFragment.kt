package com.limonalmacenes.view.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.limonalmacenes.R
import com.limonalmacenes.view.activities.DetallesArticulo
import kotlinx.android.synthetic.main.fragment_identificacion.*

class IdentificacionFragment : Fragment() {

    companion object {
        val TAG: String = IdentificacionFragment::class.java.simpleName
        fun newInstance() = IdentificacionFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_identificacion, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmIdent_btnBuscar.setOnClickListener { buscar() }

        fragm_ident_et_busqueda.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    buscar()
                    true
                }
                else -> false
            }
        }
    }

    fun irActivityDetalles() {
        Intent(context, DetallesArticulo::class.java).apply {
            putExtra("BUSQUEDA", fragm_ident_et_busqueda.text.toString())
            putExtra("METODO_BUSQUEDA", 2)
        }.also { startActivity(it) }
    }

    fun String.isValid(): Boolean = this.isNotEmpty()

    fun EditText.validate(message: String, validator: (String) -> Boolean) {
        this.error = if (validator(this.text.toString())) null else message
    }

    private fun buscar(){
        fragm_ident_et_busqueda.validate("Campo vacío") { s ->
            s.isValid().also {
                if(it){ irActivityDetalles() }
            }
        }
    }
}