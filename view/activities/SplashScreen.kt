package com.limonalmacenes.view.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle

class SplashScreen : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchMain()
    }

    fun launchMain() {
        startActivity(Intent(this, IntroActivity::class.java))
        finish()
    }

}