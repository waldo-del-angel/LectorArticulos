package com.limonalmacenes.view.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import com.limonalmacenes.R
import com.limonalmacenes.view.fragments.intro.IntroFragment
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {

    val fragment1 = IntroFragment()
    val fragment2 = IntroFragment()
    val fragment3 = IntroFragment()

    lateinit var adapter: PagerAdapter

    lateinit var preferencias: SharedPreferences
    val pref_mostrar_intro = "intro"

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        preferencias = getSharedPreferences("Introduccion", Context.MODE_PRIVATE)

        if(!preferencias.getBoolean(pref_mostrar_intro, true)) {
            irActivityMain()
        }

        initFragments()
        adapter = PagerAdapter(supportFragmentManager)

        adapter.listaFragment.add(fragment1)
        adapter.listaFragment.add(fragment2)
        adapter.listaFragment.add(fragment3)

        btnSiguiente.setOnClickListener { main_viewpager.currentItem++ }
        btnOmitir.setOnClickListener {
            irActivityMain()
            confirmarIntroLeida()
        }

        main_viewpager.adapter = adapter
        dots_indicator.setViewPager(main_viewpager)

        main_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPix: Int) {}

            override fun onPageSelected(position: Int) {
                if(position == adapter.listaFragment.size - 1) { // Ultima pagina

                    btnSiguiente.text = "HECHO"
                    btnSiguiente.setOnClickListener {
                        irActivityMain()
                        confirmarIntroLeida()
                    }
                    btnOmitir.visibility = View.INVISIBLE
                } else { // Avanzar pagina
                    btnSiguiente.text = "SIGUIENTE"
                    btnSiguiente.setOnClickListener { main_viewpager.currentItem++ }
                    btnOmitir.visibility = View.VISIBLE
                }
            }

        })
    }

    private fun initFragments() {
        fragment1.setTitulo("Lector de\ncódigo de barras")
        fragment2.setTitulo("Búsqueda por\nclave")
        fragment3.setTitulo("Búsqueda por\ndescripción")

        fragment1.setSubtitulo("Usa tu cámara para consultar los detalles de tu producto")
        fragment2.setSubtitulo("Puedes realizar la consulta a través de la clave del producto")
        fragment3.setSubtitulo("También puedes buscar tu producto por su nombre")

        fragment2.setImagen(R.drawable.slider_identificacion)
        fragment3.setImagen(R.drawable.slider_descripcion)
    }

    private fun irActivityMain() {
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }

    private fun confirmarIntroLeida() {
        val editor = preferencias.edit()
        editor.putBoolean(pref_mostrar_intro, false)
        editor.apply()
    }

    inner class PagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        val listaFragment: MutableList<Fragment> = ArrayList()
        override fun getItem(position: Int): Fragment = listaFragment[position]
        override fun getCount(): Int = listaFragment.size
    }
}
