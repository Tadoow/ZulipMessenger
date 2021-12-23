package com.example.myapplication.presentation.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.common.getFragmentComponent
import com.example.myapplication.common.viewBinding
import com.example.myapplication.databinding.UserProfileBinding
import com.example.myapplication.presentation.Factory

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(UserProfileBinding::bind)

    private val viewModel: ProfileViewModel by viewModels {
        Factory {
            getFragmentComponent().getProfileViewModel()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            viewModel.loadUserPresence()
        }
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.userPresenceLiveData.observe(viewLifecycleOwner) {
            when (it) {
                getString(R.string.user_active_status) -> binding.userStatus.apply {
                    setTextColor(ResourcesCompat.getColor(resources, R.color.light_green, null))
                    text = resources.getString(R.string.user_active_status)
                }
                getString(R.string.user_idle_status) -> binding.userStatus.apply {
                    setTextColor(ResourcesCompat.getColor(resources, R.color.orange, null))
                    text = resources.getString(R.string.user_idle_status)
                }
                getString(R.string.user_offline_status) -> binding.userStatus.apply {
                    setTextColor(ResourcesCompat.getColor(resources, R.color.red, null))
                    text = resources.getString(R.string.user_offline_status)
                }
            }
        }
        viewModel.ownUserLiveData.observe(viewLifecycleOwner) {
            Glide.with(this)
                .load(it.avatar)
                .into(binding.userAvatarInProfile)

            binding.userNameInProfile.text = it.userName
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
        }
    }

    companion object {

        const val TAG = "ProfileFragment"

        fun newInstance(): Fragment {
            return ProfileFragment()
        }
    }

}