package com.maliszew.proximitycontacttracing.views

import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.maliszew.proximitycontacttracing.databinding.RecyclerRowsBinding

class RecyclerViewAdapter : RecyclerView.Adapter<BindingViewHolder>() {
    // If your layout file is something_awesome.xml then your binding class will be SomethingAwesomeBinding
    // Since our layout file is item_movie.xml, our auto generated binding class is ItemMovieBinding
    //                          recycler_rows.xml                                   RecyclerRowsBinding

    private val items = mutableListOf<RecyclerItem>()

    override fun getItemCount(): Int {
        //Log.d("maliszew/Adapter", "size is $items.size")
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).layoutId
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: RecyclerRowsBinding = DataBindingUtil.inflate(inflater, viewType, parent, false) // ViewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BindingViewHolder,
        position: Int
    ) {
        getItem(position).bind(holder.binding)
        holder.binding.executePendingBindings()
    }

    fun updateData(newItems: List<RecyclerItem>) {
        this.items.clear()
        this.items.addAll(newItems)
        notifyDataSetChanged()
        Log.d("maliszew/Adapter", "update data is ${this.items}")
    }

    private fun getItem(position: Int): RecyclerItem {
        return items[position]
    }

}

class BindingViewHolder(
    val binding: RecyclerRowsBinding
) : RecyclerView.ViewHolder(binding.root)

@BindingAdapter("items")
fun setRecyclerViewItems(
    recyclerView: RecyclerView,
    items: List<RecyclerItem>?
) {
    var adapter = (recyclerView.adapter as? RecyclerViewAdapter)
    Log.d("maliszew/Adapter", "adapter is $adapter but items are $items")
    if (adapter == null) {
        adapter = RecyclerViewAdapter()
        recyclerView.adapter = adapter
    }
    adapter.updateData(items.orEmpty())
}