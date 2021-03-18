package ru.zaytsev.githubtest.adapters.repo

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.zaytsev.githubtest.Repo

class RepoAdapter: AsyncListDifferDelegationAdapter<Repo>(DiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(RepoAdapterDelegate())
        Log.d("ERRORVIEWMODEL", "adding delegate adapter")
    }

    class DiffUtilCallback(): DiffUtil.ItemCallback<Repo>() {
        override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem == newItem
        }
    }
}