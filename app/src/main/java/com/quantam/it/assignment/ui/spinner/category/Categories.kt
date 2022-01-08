package com.quantam.it.assignment.ui.spinner.category

import com.quantam.it.assignment.R

data class Categories(val image: Int, val categoryName: String)

object ObjCategories{

    private val images = intArrayOf(
        R.drawable.art_craft_icon,
        R.drawable.automotive_steering_icon,
        R.drawable.teddy_bear_icon,
        R.drawable.computer_icon,
        R.drawable.digital_music_icon
    )

    private val categoryNames = arrayOf(
        "Art and Crafts",
        "Automotive",
        "Baby",
        "Computer",
        "Digital News"
    )

    var list: ArrayList<Categories>? = null
    get() {
        if (field != null)
            return field

        field = ArrayList()
        for (i in images.indices){

            val categoryId = images[i]
            val categoryName = categoryNames[i]

            val cat = Categories(categoryId, categoryName)
            field!!.add(cat)
        }
        return field
    }

}