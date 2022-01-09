package com.quantam.it.assignment.ui.spinner.category

data class Codes(val code: String)

object ObjCountryCodes {

    private val countryCodes = arrayOf(
        "+91",
        "+93",
        "+355",
        "+54",
        "+61",
        "+43",
        "+375",
        "+32",
        "+229",
        "+673"
    )

    var list: ArrayList<Codes>? = null
        get() {
            if (field != null)
                return field

            field = ArrayList()
            for (i in countryCodes.indices) {

                val data = countryCodes[i]

                val countC = Codes(data)
                field!!.add(countC)
            }
            return field
        }

}