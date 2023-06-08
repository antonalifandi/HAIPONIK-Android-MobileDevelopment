package com.example.myapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.DataHidroponik
import com.example.myapp.R

class DataHidroponikAdapter(var mList: List<DataHidroponik>) : RecyclerView.Adapter<DataHidroponikAdapter.DataHidroponiKViewHolder>() {

    inner class DataHidroponiKViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val logo : ImageView = itemView.findViewById(R.id.fotoIv)
        val titleTv : TextView = itemView.findViewById(R.id.titleTv)
        val hidroponikDescTv : TextView = itemView.findViewById(R.id.hidroponikDesc)
        val constraintLayout : ConstraintLayout = itemView.findViewById(R.id.constraintLayout)

        fun  collapseExpandedView(){
            hidroponikDescTv.visibility = View.GONE
        }

    }

    fun setFilteredList(mList: List<DataHidroponik>){
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHidroponiKViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.itemhidroponik, parent, false)
        return DataHidroponiKViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: DataHidroponiKViewHolder, position: Int) {

        val DataHidroponik = mList[position]
        holder.logo.setImageResource(DataHidroponik.logo)
        holder.titleTv.text = DataHidroponik.title
        holder.hidroponikDescTv.text = DataHidroponik.desc

        val isExpandable : Boolean = DataHidroponik.isExpandable
        holder.hidroponikDescTv.visibility = if (isExpandable) View.VISIBLE else View.GONE

        holder.constraintLayout.setOnClickListener {
            isAnyItemExpanded(position)
            DataHidroponik.isExpandable = !DataHidroponik.isExpandable
            notifyItemChanged(position, Unit)
        }
    }

    private fun isAnyItemExpanded(position: Int){
        val temp = mList.indexOfFirst {
            it.isExpandable
        }

        if(temp >= 0 &&  temp != position){
            mList[temp].isExpandable = false
            notifyItemChanged(temp , 0)
        }
    }

    override fun onBindViewHolder(
        holder: DataHidroponiKViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {

        if(payloads.isNotEmpty() && payloads[0] == 0){
            holder.collapseExpandedView()
        }else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }
}

