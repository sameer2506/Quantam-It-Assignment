package com.quantam.it.assignment.ui.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.quantam.it.assignment.R
import com.quantam.it.assignment.databinding.BestDealsRecyclerItemBinding
import com.quantam.it.assignment.databinding.CategoryRecyclerViewItemViewBinding
import com.quantam.it.assignment.databinding.DealsRecyclerItemBinding
import com.quantam.it.assignment.databinding.ProductCatalogueRecyclerItemViewBinding
import com.quantam.it.assignment.modal.CategoriesData
import com.quantam.it.assignment.modal.DealsData
import com.quantam.it.assignment.modal.ProductCatalog
import com.quantam.it.assignment.utils.toast

class DealsRecyclerAdapter(
    val arrayList: ArrayList<DealsData>,
    val context: Context
) : RecyclerView.Adapter<DealsRecyclerAdapter.CategoryVH>() {

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

    class CategoryVH(private val binding: DealsRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            list: DealsData
        ) {
            binding.dealsImage.setImageResource(list.image)
            binding.textView23.text = list.text1
            binding.textView24.text = list.text2
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CategoryVH {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: DealsRecyclerItemBinding = DataBindingUtil
                    .inflate(
                        layoutInflater,
                        R.layout.deals_recycler_item,
                        parent,
                        false
                    )
                return CategoryVH(binding)
            }
        }
    }

}