package com.example.myapplication.presentation.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.presentation.models.User

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(
            TAG,
            "onViewCreated() called with: view = $view, savedInstanceState = $savedInstanceState"
        )
        val userLocal = User(1, "Donald Trump", "america_great_again@make.com", true)

        val userLocalName = view.findViewById<TextView>(R.id.user_name_in_profile)
        userLocalName.text = userLocal.userName

        val userLocalStatus = view.findViewById<TextView>(R.id.user_status)
        if (userLocal.online) {
            userLocalStatus.apply {
                setTextColor(ResourcesCompat.getColor(resources, R.color.light_green, null))
                text = resources.getString(R.string.user_online)
            }
        } else {
            userLocalStatus.apply {
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