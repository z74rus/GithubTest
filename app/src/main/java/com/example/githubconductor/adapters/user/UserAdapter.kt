package com.example.githubconductor.adapters.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubconductor.databinding.ItemUserBinding
import com.example.githubconductor.models.User

class UserAdapter(
    private val onItemClicked: (User) -> Unit
) : ListAdapter<User, UserAdapter.ViewHolder>(DiffUtilCallback()) {

    class DiffUtilCallback(): DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(
        private val binding: ItemUserBinding,
        onItemClicked: (User) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        private var currentUser: User? = null

        init {
            binding.root.setOnClickListener {
                currentUser?.let { user ->
                    onItemClicked(user)
                }
            }
        }

        fun bind(user: User) {
            currentUser = user
            binding.nicknameTv.text = user.name
            binding.followersTv.text = "Followers: ${user.countFollows}"

            Glide.with(itemView)
                .load(user.avatar)
                .into(binding.avatarIv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}