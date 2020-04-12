package com.enesdokuz.countries.view

import  android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.enesdokuz.countries.R
import com.enesdokuz.countries.adapter.CountryAdapter
import com.enesdokuz.countries.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*


class FeedFragment : Fragment() {

    private lateinit var viewModel: FeedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.refreshData()

        recyclerview_feed_countrylist.layoutManager = LinearLayoutManager(context)
        recyclerview_feed_countrylist.adapter = countryAdapter

        observeLiveData()

        swipe_feed_refresh_data.setOnRefreshListener {
            recyclerview_feed_countrylist.visibility = View.GONE
            text_feed_error_message.visibility = View.GONE
            progress_feed_loading.visibility = View.VISIBLE
            viewModel.refreshData()
            swipe_feed_refresh_data.isRefreshing = false
        }

        /*
        btn_feed_open_detail.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToDetailFragment()
            Navigation.findNavController(it).navigate(action)
        }
         */

    }

    fun observeLiveData() {
        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->

            countries?.let {
                recyclerview_feed_countrylist.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
                progress_feed_loading.visibility = View.GONE
            }

        })

        viewModel.countryError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                if (it)
                    text_feed_error_message.visibility = View.VISIBLE
                else
                    text_feed_error_message.visibility = View.GONE
            }
        })

        viewModel.countryLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                if (it) {
                    progress_feed_loading.visibility = View.VISIBLE
                    recyclerview_feed_countrylist.visibility = View.GONE
                    text_feed_error_message.visibility = View.GONE
                } else
                    progress_feed_loading.visibility = View.GONE
            }
        })
    }

}
