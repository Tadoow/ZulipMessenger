package com.example.myapplication.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.common.viewBinding
import com.example.myapplication.databinding.UserProfileBinding
import com.example.myapplication.domain.people.entity.User

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(UserProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(
            TAG,
            "onViewCreated() called with: view = $view, savedInstanceState = $savedInstanceState"
        )
        val userLocal = User(1, "Donald Trump", "america_great_again@make.com", true)
        binding.userNameInProfile.text = userLocal.userName

        if (userLocal.online) {
            binding.userStatus.apply {
                setTextColor(ResourcesCompat.getColor(resources, R.color.light_green, null))
                text = resources.getString(R.string.user_online)
            }
        } else {
            binding.userStatus.apply {
                setTextColor(ResourcesCompat.getColor(resources, R.color.red, null))
                text = resources.getString(R.string.user_offline)
            }
        }
    }

    companion object {

        const val TAG = "ProfileFragment"

        fun newInstance(): Fragment {
            return ProfileFragment()
        }
    }
}