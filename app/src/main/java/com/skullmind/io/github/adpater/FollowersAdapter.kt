package com.skullmind.io.github.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.skullmind.io.R
import com.skullmind.io.databinding.ItemGitHubBinding
import com.skullmind.io.github.GitHubNavigator
import com.skullmind.io.github.bean.User
import javax.inject.Inject


class FollowersAdapter @Inject constructor(val navigator: GitHubNavigator) : RecyclerView.Adapter<FollowersAdapter.RepoAdapterViewHolder>() {


    private var repoList: List<User>? = null

    init {
        this.repoList = emptyList<User>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoAdapterViewHolder {
        val binding = DataBindingUtil.inflate<ItemGitHubBinding>(LayoutInflater.from(parent.context), R.layout.item_git_hub, parent, false)
        binding.navigator = navigator
        return RepoAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoAdapterViewHolder, position: Int) {
        holder.bindRepo(repoList!![position])
    }

    override fun getItemCount(): Int {
        val count = repoList?.size?:0
        return count
    }

    fun setRepoList(repoList: List<User>) {
        this.repoList = repoList
        notifyDataSetChanged()
    }


    class RepoAdapterViewHolder(internal var dataBinding: ItemGitHubBinding) :
        RecyclerView.ViewHolder(dataBinding.llContent) {

        internal fun bindRepo(user: User) {
           dataBinding.user = user
        }

    }

}
