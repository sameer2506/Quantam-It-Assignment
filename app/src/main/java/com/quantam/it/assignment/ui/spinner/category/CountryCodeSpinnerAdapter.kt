package com.quantam.it.assignment.ui.spinner.category

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.quantam.it.assignment.R

class CountryCodeSpinnerAdapter(
    context: Context,
    infoList: List<Codes>
) : ArrayAdapter<Codes>(context, 0, infoList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position,convertView,parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position,convertView,parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {

        val codeData = getItem(position)

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spineer_item, parent, false)

        val text = view.findViewById<TextView>(R.id.textViewCountryCode)

        text.text = codeData!!.code

        return view
    }
}