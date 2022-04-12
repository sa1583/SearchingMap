package fastcampus.aop.part2.searchingmap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fastcampus.aop.part2.searchingmap.databinding.ItemSearchResultBinding
import fastcampus.aop.part2.searchingmap.model.SearchResultEntity

class SearchRecyclerAdapter(
    private val callback: (SearchResultEntity) -> Unit
) : ListAdapter<SearchResultEntity, SearchRecyclerAdapter.SearchResultItemViewHolder>(diffUtil) {

    inner class SearchResultItemViewHolder(
        private val binding: ItemSearchResultBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchResultEntity) {
            binding.titleTextView.text = item.name
            binding.subTitleTextView.text = item.fullAddress

            itemView.setOnClickListener {
                callback(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultItemViewHolder {
        return SearchResultItemViewHolder(
            ItemSearchResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchResultItemViewHolder, position: Int) {
        currentList[position].also {
            holder.bind(it)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SearchResultEntity>() {
            override fun areItemsTheSame(oldItem: SearchResultEntity, newItem: SearchResultEntity): Boolean {
                return oldItem.locationLatLng == newItem.locationLatLng
            }

            override fun areContentsTheSame(oldItem: SearchResultEntity, newItem: SearchResultEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

}