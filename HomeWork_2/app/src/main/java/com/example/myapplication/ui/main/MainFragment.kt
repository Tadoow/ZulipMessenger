package com.example.myapplication.ui.main

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.common.viewBinding
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.ui.channels.ChannelsFragment
import com.example.myapplication.ui.people.PeopleFragment
import com.example.myapplication.ui.profile.ProfileFragment

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding by viewBinding(FragmentMainBinding::bind)

    private lateinit var channelsFragment: Fragment
    private lateinit var peopleFragment: Fragment
    private lateinit var profileFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called with: savedInstanceState = $savedInstanceState")

        channelsFragment = ChannelsFragment.newInstance()
        peopleFragment = PeopleFragment.newInstance()
        profileFragment = ProfileFragment.newInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(
            TAG,
            "onViewCreated() called with: view = $view, savedInstanceState = $savedInstanceState"
        )

        setTabFragment(channelsFragment, ChannelsFragment.TAG)
        setActionBar("Search...")

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.channels -> {
                    setTabFragment(channelsFragment, ChannelsFragment.TAG)
                    setActionBar("Search...")
                }
                R.id.people -> {
                    setTabFragment(peopleFragment, PeopleFragment.TAG)
                    setActionBar("Users...")
                }
                R.id.profile -> {
                    setTabFragment(profileFragment, ProfileFragment.TAG)
                    (activity as MainActivity).supportActionBar?.hide()
                }
            }
            true
        }

    }

    private fun setActionBar(title: String) {
        val actionBar = (activity as MainActivity).supportActionBar!!
        val actionBarColor = ResourcesCompat.getColor(resources, R.color.light_black_1, null)
        actionBar.apply {
            this.title = title
            setBackgroundDrawable(ColorDrawable(actionBarColor))
            setDisplayHomeAsUpEnabled(false)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
        if (!actionBar.isShowing) {
            actionBar.show()
        }
    }

    private fun setTabFragment(fragment: Fragment, fragmentTag: String) {
        val fragmentInStack = childFragmentManager.findFragmentByTag(fragmentTag)
        if (fragmentInStack != null) {
            Log.d(
                TAG,
                "setTabFragment() found fragment $fragmentInStack"
            )
            childFragmentManager.beginTransaction()
                .replace(R.id.fragments_Container, fragmentInStack, fragmentTag)
                .commit()
        } else {
            Log.d(
                TAG,
                "setTabFragment() adding to backStack: fragment = $fragment, fragmentHash = ${fragment.hashCode()} fragmentInStack = $fragmentInStack"
            )
            childFragmentManager.beginTransaction()
                .replace(R.id.fragments_Container, fragment, fragmentTag)
                .addToBackStack(null)
                .commit()
        }
    }

    companion object {

        const val TAG = "MainFragment"

        fun newInstance(): Fragment {
            return MainFragment()
        }
    }
}