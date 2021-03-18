package ru.zaytsev.githubtest.adapters.repo

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import ru.zaytsev.githubtest.Repo
import ru.zaytsev.githubtest.databinding.ItemRepoBinding

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
        Log.d("ERRORVIEWMODEL", "onBindViewHolder")
        holder.bind(item)
    }


    class Holder(private val binding: ItemRepoBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            Log.d("ERRORVIEWMODEL", "init holder")
        }

        fun bind(repo: Repo) {
            Log.d("ERRORVIEWMODEL", "inside adapter $repo")
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