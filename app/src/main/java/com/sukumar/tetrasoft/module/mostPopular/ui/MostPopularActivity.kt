package com.sukumar.tetrasoft.module.mostPopular.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sukumar.tetrasoft.R
import com.sukumar.tetrasoft.base.KotlinEvent
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
            viewModel.fetchMostPopular()
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
                Toast.makeText(this, "Clicked...", Toast.LENGTH_LONG).show()
            }
            mMostPopularRecyclerView.layoutManager=layoutManager
            mMostPopularRecyclerView.adapter=mostPopularAdapter
        })
    }

    private fun showProgress(){
        mProgressBar.visibility= View.VISIBLE
    }

    private fun hideProgress(){
        mProgressBar.visibility=View.GONE
    }

    private fun showErrorMsg(error : String?){
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        mViewModel?.disposeRequest()
        super.onDestroy()
    }
}
