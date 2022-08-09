package com.githubapp.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.githubapp.R
import com.githubapp.databinding.FragmentAuthBinding
import com.githubapp.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class AuthFragment : Fragment(), KoinComponent {
    companion object {
        fun newInstance() = AuthFragment()
        const val TOKEN_URI = "https://github.com/login/oauth/access_token"
        const val RESPONSE_TYPE = "?response_type=code"
        const val SCOPE = "&scope=user,repo"
        const val AUTH_URL = "https://github.com/login/oauth/authorize"
        const val CLIENT_ID = "6edffae6fca55f3c8df7"
        const val CLIENT_SECRET = "c62dc83c42cea46eec4f6c3e91e2b8c89a57e28a"
        const val CALLBACK_URL = "githubapp://callback"
    }

    private var _binding: FragmentAuthBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: AuthViewModel by viewModel<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val intent = Intent(
            Intent.ACTION_VIEW, Uri.parse(
                AUTH_URL
                        + RESPONSE_TYPE
                        + "&client_id="
                        + CLIENT_ID
                        + "&redirect_uri="
                        + CALLBACK_URL
                        + SCOPE
            )
        )

        binding.loginBtn.setOnClickListener {
            startActivity(intent)
            val uri: Uri = intent.data!!
            if (uri != null && uri.toString().startsWith(CALLBACK_URL)) {
                val code = uri.getQueryParameter("code").toString()
                viewModel.getToken(CLIENT_ID, CLIENT_SECRET, code)
            }
        }

        binding.showList.setOnClickListener {
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .add(R.id.container, ListRepoFragment.newInstance())
                    .addToBackStack("")
                    .commitAllowingStateLoss()

            }
        }

    }
}