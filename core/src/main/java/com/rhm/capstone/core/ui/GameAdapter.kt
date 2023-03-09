package com.rhm.capstone.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rhm.capstone.core.R
import com.rhm.capstone.core.databinding.ItemListGameBinding
import com.rhm.capstone.core.domain.model.Game

class GameAdapter(
    private val onClickItem: (Game) -> Unit,
) : ListAdapter<Game, GameAdapter.ListViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_game, parent, false))

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
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

                root.setOnClickListener { onClickItem(data) }
            }
        }
    }

    private companion object DiffCallback : DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem == newItem
        }
    }
}