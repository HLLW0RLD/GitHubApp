package com.githubapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.githubapp.R
import com.githubapp.databinding.FragmentListRepoBinding
import com.githubapp.model.data.AccessToken
import com.githubapp.utils.AppState
import com.githubapp.utils.hide
import com.githubapp.utils.show
import com.githubapp.viewmodel.AuthViewModel
import com.githubapp.viewmodel.ReposViewModel
import org.koin.core.component.KoinComponent
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListRepoFragment : Fragment(), KoinComponent{
    companion object{
        const val BUNDLE_EXTRA = "token"

        fun newInstance() = ListRepoFragment()
    }

    private var _binding : FragmentListRepoBinding? = null
    private val binding
        get() = _binding!!
    private val adapter = ReposAdapter()
    private val viewModel: ReposViewModel by viewModel<ReposViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListRepoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listOfRepos.adapter = adapter
        adapter.setOnItemViewClickListener { repo ->
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .add(R.id.container, RepoDetailsFragment.newInstance(Bundle().apply {
                        putParcelable(RepoDetailsFragment.BUNDLE_EXTRA, repo)
                    }))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
        viewModel.reposLiveData.observe(viewLifecycleOwner) { renderData(it) }
        viewModel.getUser().let {
            viewModel.getRepos(it.toString()) }
    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.Success -> {
                binding.loadingLayout.hide()
                adapter.setData(data.repos)
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
}