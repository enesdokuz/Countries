package com.enesdokuz.countries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.enesdokuz.countries.R
import com.enesdokuz.countries.model.Country
import com.enesdokuz.countries.util.loadImage
import com.enesdokuz.countries.util.placeholderProgressBar
import com.enesdokuz.countries.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.item_country_layout.view.*

/***
 * Countries
 * enesdokuz enesdokuz 12.04.2020
 ***/
class CountryAdapter(val countryList: ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    class CountryViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_country_layout,parent,false)
        return CountryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.view.textview_itemcountry_name.text = countryList[position].name
        holder.view.textview_itemcountry_region.text = countryList[position].region
        holder.view.imageview_itemcountry_flag.loadImage(countryList[position].imageUrl,
            placeholderProgressBar(holder.view.context))

        holder.view.setOnClickListener{
            val action = FeedFragmentDirections.actionFeedFragmentToDetailFragment(countryUuid = countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun updateCountryList(newCountryList: List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}