package com.quantam.it.assignment.ui.spinner.category

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.quantam.it.assignment.R

class CategorySpinnerAdapter(
    context: Context,
    infoList: List<Categories>
) : ArrayAdapter<Categories>(context, 0, infoList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position,convertView,parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position,convertView,parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {

        val categoryData = getItem(position)

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.category_recycler_view_item_view, parent, false)

        val img = view.findViewById<ImageView>(R.id.category_img)
        val text = view.findViewById<TextView>(R.id.textView12)

        img.setImageResource(categoryData!!.image)
        text.text = categoryData.categoryName

        return view
    }
}