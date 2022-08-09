package com.githubapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.githubapp.databinding.FragmentAuthBinding
import com.githubapp.databinding.FragmentDetailsRepoBinding
import com.githubapp.model.data.Repo
import com.githubapp.utils.AppState
import com.githubapp.utils.hide
import com.githubapp.utils.show
import com.githubapp.viewmodel.ReposViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepoDetailsFragment : Fragment() {
    companion object{
        const val BUNDLE_EXTRA = "repo"

        fun newInstance(bundle: Bundle): RepoDetailsFragment {
            val fragment = RepoDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: FragmentDetailsRepoBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: ReposViewModel by viewModel<ReposViewModel>()
    private lateinit var repoBundle: Repo

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsRepoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repoBundle = arguments?.getParcelable<Repo>(BUNDLE_EXTRA)!!
        viewModel.reposLiveData.observe(viewLifecycleOwner) { renderData(it) }
        viewModel.getUser().let {
            viewModel.getRepos(it.toString()) }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.loadingLayout.hide()
                repoShowDetails(appState.repos[0])
            }
            is AppState.Loading -> {
                binding.loadingLayout.show()
            }
            is AppState.Error -> {
                binding.loadingLayout.hide()
                Toast.makeText(context, "Loading fails, please try later", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun repoShowDetails(repo: Repo) {
        with(binding){
            name.text = repo.name
            author.text = repo.owner.login
            createData.text = repo.created_at
            description.text = repo.description
            forks.text = repo.forks_count.toString()
            watchers.text = repo.watchers_count.toString()
            Glide
                .with(binding.root)
                .load(repo.image.avatar_url)
                .into(authorImage)
        }

    }

}