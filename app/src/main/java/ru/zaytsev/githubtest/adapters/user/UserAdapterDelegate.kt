package ru.zaytsev.githubtest.adapters.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import ru.zaytsev.githubtest.User
import ru.zaytsev.githubtest.databinding.ItemUserBinding

class UserAdapterDelegate(
    private val onItemClicked: (User) -> Unit
) : AbsListItemAdapterDelegate<User, User, UserAdapterDelegate.Holder>() {

    override fun isForViewType(
        item: User,
        items: MutableList<User>,
        position: Int
    ): Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return Holder(binding, onItemClicked)
    }

    override fun onBindViewHolder(item: User, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        private val binding: ItemUserBinding,
        onItemClicked: (User) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentUser: User? = null

        init {
            binding.root.setOnClickListener {
                currentUser?.let{ user ->
                    onItemClicked(user)
                }
            }
        }

        fun bind(user: User) {
            currentUser = user
            binding.nicknameTv.text = user.name

            Glide.with(itemView)
                .load(user.avatar)
                .into(binding.avatarIv)
        }
    }
}