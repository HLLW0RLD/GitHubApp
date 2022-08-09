package com.githubapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.githubapp.databinding.ItemRepoBinding
import com.githubapp.model.data.Repo

class ReposAdapter : RecyclerView.Adapter<ReposAdapter.MainViewHolder>() {

    private var repoData: List<Repo> = listOf()
    private var onItemViewClickListener: (Repo) -> Unit = {}

    fun setOnItemViewClickListener(onItemViewClickListener: (Repo) -> Unit){
        this.onItemViewClickListener = onItemViewClickListener
    }

    fun setData(data: List<Repo>) {
        repoData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(repoData[position])
    }

    override fun getItemCount(): Int {
        return repoData.size
    }

    inner class MainViewHolder(val binding: ItemRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repo: Repo) {
            binding.apply {
                author.text = repo.owner.login
                name.text = repo.name
                description.text = repo.description
                Glide
                    .with(binding.root)
                    .load(repo.image.avatar_url)
                    .into(authorImage)

                root.setOnClickListener {
                    onItemViewClickListener(repo)
                }
            }
        }
    }
}