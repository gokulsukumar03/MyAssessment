package com.sukumar.tetrasoft.module.mostPopular.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sukumar.tetrasoft.R
import com.sukumar.tetrasoft.module.mostPopular.dto.ResultsItem
import kotlinx.android.synthetic.main.adapter_most_popular.view.*

class MostPopularAdapter(
    val mContext: Context, val results: ArrayList<ResultsItem?>?, val clickCardListener: (result: ResultsItem) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var mResult : ResultsItem?=null

    override fun getItemViewType(position: Int): Int {
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder?
        val inflater = LayoutInflater.from(mContext)

        when (viewType) {
            1 -> {
                val view = inflater.inflate(R.layout.adapter_most_popular, parent, false)
                viewHolder = MostPopularViewHolder(view)
            }
            else -> {
                val view = inflater.inflate(R.layout.adapter_most_popular, parent, false)
                viewHolder = MostPopularViewHolder(view)
            }
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return results?.size?:0
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            1 -> {
                (viewHolder as MostPopularViewHolder).apply {
                    viewHolder.itemView.apply {
                        mResult = results?.get(position)
                        mResult?.let {
                            result->
                            movieTitleTv.text = result.title ?:""
                            writtenByTv.text = result.byline ?:""
                            movieDateTv.text = result.publishedDate ?:""
                            mCardView.setOnClickListener { clickCardListener(result) }
                        }
                    }
                }
            }
            else -> {

            }
        }
    }

    class MostPopularViewHolder(view: View) : RecyclerView.ViewHolder(view)
}