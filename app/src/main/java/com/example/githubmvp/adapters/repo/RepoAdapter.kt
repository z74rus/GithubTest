package com.example.githubmvp.adapters.repo

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.example.githubmvp.models.Repo

class RepoAdapter: AsyncListDifferDelegationAdapter<Repo>(DiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(RepoAdapterDelegate())
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