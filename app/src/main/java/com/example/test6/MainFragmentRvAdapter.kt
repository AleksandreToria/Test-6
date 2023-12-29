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

    private val viewTypeNumber = 1
    private val viewTypeFingerprint = 2
    private val viewTypeDelete = 3

    class NumberViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data, onItemClick: (Data) -> Unit) {
            with(binding.button) {
                if (data.type == Type.NUMBER) {
                    text = data.number.toString()
                    setOnClickListener { onItemClick(data) }
                } else {
                    text = ""
                    setOnClickListener(null)
                }
            }
        }
    }

    class ImageViewHolder(private val binding: ImageItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data, onItemClick: (Data) -> Unit) {
            when (data.type) {
                Type.FINGERPRINT -> {
                    with(binding.imageButton) {
                        setImageResource(data.imageRes)
                        setOnClickListener { onItemClick(data) }
                    }
                }

                Type.DELETE -> {
                    with(binding.imageButton) {
                        setImageResource(data.imageRes)
                        setOnClickListener { onItemClick(data) }
                    }
                }

                else -> {
                    with(binding.imageButton) {
                        setImageResource(0)
                        setOnClickListener(null)
                    }
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
            viewTypeNumber -> {
                val binding = ItemLayoutBinding.inflate(inflater, parent, false)
                NumberViewHolder(binding)
            }

            viewTypeFingerprint -> {
                val binding = ImageItemLayoutBinding.inflate(inflater, parent, false)
                ImageViewHolder(binding)
            }

            viewTypeDelete -> {
                val binding = ImageItemLayoutBinding.inflate(inflater, parent, false)
                ImageViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)
        when (holder) {
            is NumberViewHolder -> holder.bind(currentItem, onItemClick)
            is ImageViewHolder -> holder.bind(currentItem, onItemClick)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentItem = getItem(position)
        return when (currentItem.type) {
            Type.NUMBER -> viewTypeNumber
            Type.FINGERPRINT -> viewTypeFingerprint
            Type.DELETE -> viewTypeDelete
        }
    }
}



