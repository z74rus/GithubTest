package ru.zaytsev.githubtest.adapters.user

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.zaytsev.githubtest.User

class UserAdapter(
    private val onItemClicked: (User) -> Unit
) :
    AsyncListDifferDelegationAdapter<User>(DiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(UserAdapterDelegate(onItemClicked))
    }

    class DiffUtilCallback() : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}