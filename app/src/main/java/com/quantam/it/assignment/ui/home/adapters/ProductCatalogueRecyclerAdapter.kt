package com.quantam.it.assignment.ui.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.quantam.it.assignment.R
import com.quantam.it.assignment.databinding.ProductCatalogueRecyclerItemViewBinding
import com.quantam.it.assignment.modal.ProductCatalog
import com.quantam.it.assignment.utils.toast

class ProductCatalogueRecyclerAdapter(
    private val arrayList: ArrayList<ProductCatalog>,
    val context: Context
) : RecyclerView.Adapter<ProductCatalogueRecyclerAdapter.CategoryVH>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryVH = CategoryVH.from(parent)

    override fun onBindViewHolder(holder: CategoryVH, position: Int) {
        holder.bind(arrayList[position])
    }

    override fun getItemCount(): Int {
        if (arrayList.isEmpty())
            context.toast("List is empty.")
        return arrayList.size
    }

    class CategoryVH(private val binding: ProductCatalogueRecyclerItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            list: ProductCatalog
        ) {
            binding.product = list
            binding.imageView5.setImageResource(list.image)
            binding.textView14.text = list.discount.toString()
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CategoryVH {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: ProductCatalogueRecyclerItemViewBinding = DataBindingUtil
                    .inflate(
                        layoutInflater,
                        R.layout.product_catalogue_recycler_item_view,
                        parent,
                        false
                    )
                return CategoryVH(binding)
            }
        }
    }

}