package com.githubapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.githubapp.databinding.FragmentAuthBinding
import com.githubapp.databinding.FragmentDetailsRepoBinding

class RepoDetailsFragment : Fragment() {
    companion object{
        fun newInstance(){

        }
    }

    private var _binding: FragmentDetailsRepoBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsRepoBinding.inflate(inflater, container, false)
        return binding.root
    }

}