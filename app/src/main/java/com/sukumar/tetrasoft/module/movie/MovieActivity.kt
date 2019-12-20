package com.sukumar.tetrasoft.module.movie

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sukumar.movieapiapplication.ui.MovieViewModel
import com.sukumar.tetrasoft.R
import com.sukumar.tetrasoft.base.KotlinEvent
import com.sukumar.tetrasoft.module.common.AppConstants
import com.sukumar.tetrasoft.module.common.NetworkAvailability
import com.sukumar.tetrasoft.module.mostPopular.ui.MoviesAdapter
import com.sukumar.tetrasoft.module.webview.WebViewActivity
import kotlinx.android.synthetic.main.activity_movie.*

class MovieActivity : AppCompatActivity() {

    private var mViewModel : MovieViewModel?=null
    private var mostPopularAdapter : MoviesAdapter?=null
    private var layoutManager : LinearLayoutManager?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        initViewModel()
    }

    private fun initViewModel(){
        mViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        mViewModel?.let {
                viewModel->
            if(NetworkAvailability.isNetworkAvailable(this)) {
                viewModel.fetchMostPopular()
            }else{
                showNoInternet()
            }
            viewModelListener()
        }
    }

    private fun viewModelListener(){
        mViewModel?.eventLiveData?.observe(this, Observer {
                event->
            event?.let {
                when(event){
                    is KotlinEvent.Companion.LoadingEvent -> {showProgress()}
                    is KotlinEvent.Companion.CompletedEvent -> {hideProgress()}
                    is KotlinEvent.Companion.FailedEvent -> { showErrorMsg(event.error)}
                }
            }
        })

        mViewModel?.mostPopularLiveData?.observe(this, Observer {
                mostPopular->
            layoutManager = LinearLayoutManager(this)
            mostPopularAdapter = MoviesAdapter(this, mostPopular.search) {
                    result->
                 startActivity(Intent(this@MovieActivity, WebViewActivity::class.java))
            }
            mMostPopularRecyclerView?.layoutManager=layoutManager
            mMostPopularRecyclerView?.adapter=mostPopularAdapter
        })
    }

    private fun showProgress(){
        mProgressBar.visibility= View.VISIBLE
    }

    private fun hideProgress(){
        mProgressBar.visibility= View.INVISIBLE
    }

    private fun showErrorMsg(error : String?){
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    private fun showNoInternet(){
        Toast.makeText(this, AppConstants.INTERNET_MSG, Toast.LENGTH_LONG).show()

    }

    override fun onDestroy() {
        mViewModel?.disposeRequest()
        super.onDestroy()
    }
}
