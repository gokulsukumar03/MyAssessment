package com.sukumar.tetrasoft.module.mostPopular.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sukumar.tetrasoft.R
import com.sukumar.tetrasoft.base.KotlinEvent
import com.sukumar.tetrasoft.module.common.AppConstants
import com.sukumar.tetrasoft.module.common.AppConstants.Companion.INTENT_KEY
import com.sukumar.tetrasoft.module.common.NetworkAvailability
import com.sukumar.tetrasoft.module.moviedetail.MovieDetailActivity
import kotlinx.android.synthetic.main.activity_most_popular.*

class MostPopularActivity : AppCompatActivity() {

    private var mViewModel : MostPopularViewModel?=null
    private var mostPopularAdapter : MostPopularAdapter?=null
    private var layoutManager : LinearLayoutManager?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_most_popular)
        initViewModel()
    }

    private fun initViewModel(){
        mViewModel = ViewModelProviders.of(this).get(MostPopularViewModel::class.java)
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
            mMostPopularRecyclerView.setHasFixedSize(false)
            mostPopularAdapter = MostPopularAdapter(this, mostPopular.results) {
                result->
                startActivity(Intent(this@MostPopularActivity, MovieDetailActivity::class.java).putExtra(INTENT_KEY , result.title?:""))
            }
            mMostPopularRecyclerView.layoutManager=layoutManager
            mMostPopularRecyclerView.adapter=mostPopularAdapter
        })
    }

    private fun showProgress(){
        mProgressBar.visibility= View.VISIBLE
    }

    private fun hideProgress(){
        mProgressBar.visibility=View.INVISIBLE
    }

    private fun showErrorMsg(error : String?){
        errorTv.visibility=View.VISIBLE
        errorTv.text=error
    }

    private fun showNoInternet(){
        errorTv.visibility=View.VISIBLE
        errorTv.text=AppConstants.INTERNET_MSG
    }

    override fun onDestroy() {
        mViewModel?.disposeRequest()
        super.onDestroy()
    }

}
