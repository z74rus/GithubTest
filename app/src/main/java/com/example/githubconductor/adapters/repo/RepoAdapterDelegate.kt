package com.example.githubconductor.adapters.repo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubconductor.databinding.ItemRepoBinding
import com.example.githubconductor.models.Repo
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class RepoAdapterDelegate: AbsListItemAdapterDelegate<Repo, Repo, RepoAdapterDelegate.Holder>() {

    override fun isForViewType(
        item: Repo,
        items: MutableList<Repo>,
        position: Int
    ): Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        val binding = ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(item: Repo, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }


    class Holder(private val binding: ItemRepoBinding): RecyclerView.ViewHolder(binding.root) {

        init {

        }

        fun bind(repo: Repo) {
            with(binding) {
                nameRepoTv.text = repo.name
                descriptionTv.text = repo.description
                dateTv.text = repo.updated
                mergeTv.text = repo.defaultBranch
                starTv.text = repo.starsCount.toString()
                languageTv.text = repo.language
            }
        }
    }


}