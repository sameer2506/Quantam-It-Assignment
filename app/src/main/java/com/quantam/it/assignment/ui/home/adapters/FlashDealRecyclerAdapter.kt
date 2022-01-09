package com.quantam.it.assignment.ui.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.quantam.it.assignment.R
import com.quantam.it.assignment.databinding.FlashDealRecyclerItemViewBinding
import com.quantam.it.assignment.modal.FlashDealData
import com.quantam.it.assignment.utils.toast

class FlashDealRecyclerAdapter(
    private val arrayList: ArrayList<FlashDealData>,
    val context: Context
) : RecyclerView.Adapter<FlashDealRecyclerAdapter.CategoryVH>() {

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

    class CategoryVH(private val binding: FlashDealRecyclerItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            list: FlashDealData
        ) {
            binding.imgProduct.setImageResource(list.image)
            binding.textView22.text = "$${list.amount}"
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CategoryVH {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: FlashDealRecyclerItemViewBinding = DataBindingUtil
                    .inflate(
                        layoutInflater,
                        R.layout.flash_deal_recycler_item_view,
                        parent,
                        false
                    )
                return CategoryVH(binding)
            }
        }
    }

}