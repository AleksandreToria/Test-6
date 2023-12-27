package com.example.test6

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.test6.data.Data
import com.example.test6.data.Type
import com.example.test6.databinding.ImageItemLayoutBinding
import com.example.test6.databinding.ItemLayoutBinding

class MainFragmentRvAdapter(private val onItemClick: (Data) -> Unit) :
    ListAdapter<Data, RecyclerView.ViewHolder>(DataDiffCallback()) {

    private val VIEW_TYPE_NUMBER = 1
    private val VIEW_TYPE_FINGERPRINT = 2
    private val VIEW_TYPE_DELETE = 3

    class NumberViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data, onItemClick: (Data) -> Unit) {
            with(binding) {
                if (data.type == Type.NUMBER) {
                    button.text = data.number.toString()
                    button.setOnClickListener { onItemClick(data) }
                } else {
                    button.text = ""
                    button.setOnClickListener(null)
                }
            }
        }
    }

    class FingerprintViewHolder(private val binding: ImageItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data, onItemClick: (Data) -> Unit) {
            when (data.type) {
                Type.FINGERPRINT -> {
                    binding.imageButton.setImageResource(data.imageRes)
                    binding.imageButton.setOnClickListener { onItemClick(data) }
                }

                Type.DELETE -> {
                    binding.imageButton.setImageResource(data.imageRes)
                    binding.imageButton.setOnClickListener { onItemClick(data) }
                }

                else -> {
                    binding.imageButton.setImageResource(0)
                    binding.imageButton.setOnClickListener(null)
                }
            }
        }
    }


    private class DataDiffCallback : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_NUMBER -> {
                val binding = ItemLayoutBinding.inflate(inflater, parent, false)
                NumberViewHolder(binding)
            }

            VIEW_TYPE_FINGERPRINT -> {
                val binding = ImageItemLayoutBinding.inflate(inflater, parent, false)
                FingerprintViewHolder(binding)
            }

            VIEW_TYPE_DELETE -> {
                val binding = ImageItemLayoutBinding.inflate(inflater, parent, false)
                FingerprintViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)
        when (holder) {
            is NumberViewHolder -> holder.bind(currentItem, onItemClick)
            is FingerprintViewHolder -> holder.bind(currentItem, onItemClick)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentItem = getItem(position)
        return when (currentItem.type) {
            Type.NUMBER -> VIEW_TYPE_NUMBER
            Type.FINGERPRINT -> VIEW_TYPE_FINGERPRINT
            Type.DELETE -> VIEW_TYPE_DELETE
        }
    }
}



