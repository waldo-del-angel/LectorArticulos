package com.limonalmacenes.view.fragments.intro

import android.media.Image
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.limonalmacenes.R
import kotlinx.android.synthetic.main.fragment_intro.*

class IntroFragment: Fragment() {
    private var titulo: String = ""
    private var subtitulo: String = ""
    private var img: Int = R.drawable.slider_codbarras

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_intro, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intro_titulo.text = titulo
        intro_subtitulo.text = subtitulo
        intro_img.setImageResource(img)
    }

    fun setTitulo(titulo: String){
        this.titulo = titulo
    }

    fun setSubtitulo(subtitulo: String){
        this.subtitulo = subtitulo
    }

    fun setImagen(imgResource: Int){
        this.img = imgResource
    }
}