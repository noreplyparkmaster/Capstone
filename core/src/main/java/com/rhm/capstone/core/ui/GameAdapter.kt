package com.rhm.capstone.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rhm.capstone.core.R
import com.rhm.capstone.core.databinding.ItemListGameBinding
import com.rhm.capstone.core.domain.model.Game
import java.util.ArrayList

class GameAdapter : RecyclerView.Adapter<GameAdapter.ListViewHolder>() {

    private var listData = ArrayList<Game>()
    var onItemClick: ((Game) -> Unit)? = null

    fun setData(newListData: List<Game>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_game, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListGameBinding.bind(itemView)
        fun bind(data: Game) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.imageUrl)
                    .into(ivItemImage)
                tvItemName.text = data.name
                tvItemYear.text = data.releaseDate.substringBefore("-")
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}