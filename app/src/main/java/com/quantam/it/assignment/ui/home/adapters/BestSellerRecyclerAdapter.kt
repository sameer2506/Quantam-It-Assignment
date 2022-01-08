package com.quantam.it.assignment.ui.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.quantam.it.assignment.R
import com.quantam.it.assignment.databinding.BestDealsRecyclerItemBinding
import com.quantam.it.assignment.databinding.CategoryRecyclerViewItemViewBinding
import com.quantam.it.assignment.databinding.ProductCatalogueRecyclerItemViewBinding
import com.quantam.it.assignment.modal.CategoriesData
import com.quantam.it.assignment.modal.ProductCatalog
import com.quantam.it.assignment.utils.toast

class BestSellerRecyclerAdapter(
    private val arrayList: ArrayList<ProductCatalog>,
    val context: Context
) : RecyclerView.Adapter<BestSellerRecyclerAdapter.CategoryVH>() {

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

    class CategoryVH(private val binding: BestDealsRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            list: ProductCatalog
        ) {
            binding.bestSellerOffer.text = "Up to ${list.discount}% Off"
            binding.imageView6.setImageResource(list.image)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CategoryVH {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: BestDealsRecyclerItemBinding = DataBindingUtil
                    .inflate(
                        layoutInflater,
                        R.layout.best_deals_recycler_item,
                        parent,
                        false
                    )
                return CategoryVH(binding)
            }
        }
    }

}