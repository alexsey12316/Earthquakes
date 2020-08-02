package com.probation.myapplication.ui.earthquakes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.probation.myapplication.database.Earthquake
import com.probation.myapplication.databinding.EarthquakeListItemBinding

class EarthquakeAdapter(val clickListener: EarthquakeClickListener)
    :ListAdapter<Earthquake,EarthquakeAdapter.ViewHolder>(EarthquakeDiffCallback()) {

    class ViewHolder private constructor(val binding:EarthquakeListItemBinding)
        :RecyclerView.ViewHolder(binding.root) {

            fun bind(earthquake: Earthquake,clickListener: EarthquakeClickListener) {
                binding.apply {
                    this.earthquake=earthquake
                    this.clickListener=clickListener
                    executePendingBindings()
                }
            }
        companion object
        {
            fun from(parent:ViewGroup):ViewHolder
            {
                val layoutInflater=LayoutInflater.from(parent.context)
                val binding=EarthquakeListItemBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=getItem(position)
        holder.bind(item,clickListener)
    }
}


class EarthquakeDiffCallback:DiffUtil.ItemCallback<Earthquake>()
{
    override fun areItemsTheSame(oldItem: Earthquake, newItem: Earthquake) =oldItem.publicID==newItem.publicID

    override fun areContentsTheSame(oldItem: Earthquake, newItem: Earthquake)=oldItem==newItem
}


class EarthquakeClickListener(val listener:(id:String)->Unit)
{
    fun onClick(earthquake: Earthquake)
    {
        listener(earthquake.publicID)
    }
}