package com.quantam.it.assignment.ui.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.quantam.it.assignment.R
import com.quantam.it.assignment.databinding.CategoryRecyclerViewItemViewBinding
import com.quantam.it.assignment.modal.CategoriesData
import com.quantam.it.assignment.utils.toast

class CategoryRecyclerAdapter(
    val arrayList: ArrayList<CategoriesData>,
    val context: Context
) : RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryVH>() {

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


    class CategoryVH(private val binding: CategoryRecyclerViewItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            list: CategoriesData
        ) {
            binding.category = list
            binding.categoryImg.setImageResource(list.image)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CategoryVH {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: CategoryRecyclerViewItemViewBinding = DataBindingUtil
                    .inflate(
                        layoutInflater,
                        R.layout.category_recycler_view_item_view,
                        parent,
                        false
                    )
                return CategoryVH(binding)
            }
        }
    }

}