package com.sukumar.tetrasoft.module.moviedetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sukumar.tetrasoft.R
import com.sukumar.tetrasoft.module.common.AppConstants.Companion.INTENT_KEY
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        if(intent.hasExtra(INTENT_KEY)){
            movieDetailTv.text=intent.getStringExtra(INTENT_KEY)
        }
    }


}
