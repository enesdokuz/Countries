package com.enesdokuz.countries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.enesdokuz.countries.R
import com.enesdokuz.countries.databinding.ItemCountryLayoutBinding
import com.enesdokuz.countries.model.Country
import com.enesdokuz.countries.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.item_country_layout.view.*

/***
 * Countries
 * enesdokuz enesdokuz 12.04.2020
 ***/
class CountryAdapter(val countryList: ArrayList<Country>) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(),CountryClickListener{

    class CountryViewHolder(var view: ItemCountryLayoutBinding) :
        RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemCountryLayoutBinding>(
            inflater,
            R.layout.item_country_layout,
            parent,
            false
        )
        return CountryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.view.country = countryList[position]
        holder.view.listener = this
    }

    fun updateCountryList(newCountryList: List<Country>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun onClicked(v: View) {
        val uuid = v.textview_itemcountry_uuid.text.toString().toInt()
        val action = FeedFragmentDirections.actionFeedFragmentToDetailFragment(uuid)
        Navigation.findNavController(v).navigate(action)
    }
}