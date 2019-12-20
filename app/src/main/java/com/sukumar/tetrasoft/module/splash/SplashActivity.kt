package com.sukumar.tetrasoft.module.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.sukumar.tetrasoft.R
import com.sukumar.tetrasoft.module.movie.MovieActivity

class SplashActivity : AppCompatActivity() {

    private var handler : Handler?=null
    private var runnable : Runnable?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        handler = Handler()
        runnable = Runnable {
            startActivity(Intent(this, MovieActivity::class.java))
        }
        handler?.postDelayed(runnable, 2000)

    }

    override fun onStop() {
        handler?.let {
            h->
            h.removeCallbacks(runnable)
        }
        super.onStop()
    }



}
