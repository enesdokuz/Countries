package com.enesdokuz.countries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.enesdokuz.countries.R
import com.enesdokuz.countries.util.loadImage
import com.enesdokuz.countries.util.placeholderProgressBar
import com.enesdokuz.countries.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private var countryUuid = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid = DetailFragmentArgs.fromBundle(it).countryUuid
        }

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.getDataFromDatabase(countryUuid)

        observeLiveData()
    }

    fun observeLiveData() {
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->
            country?.let {
                textview_detail_name.text = country.name
                textview_detail_capital.text = country.capital
                textview_detail_currency.text = country.currency
                textview_detail_language.text = country.language
                textview_detail_region.text = country.region
                context?.let {
                    imageview_detail_flag.loadImage(country.imageUrl, placeholderProgressBar(it))
                }
            }

        })
    }

}
