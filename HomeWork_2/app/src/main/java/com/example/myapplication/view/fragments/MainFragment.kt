package com.example.myapplication.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainFragment : Fragment() {

    private var bottomNavigationView: BottomNavigationView? = null
    private var channelsFragment: Fragment? = null
    private var peopleFragment: Fragment? = null
    private var profileFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        channelsFragment = ChannelsFragment.newInstance()
        peopleFragment = PeopleFragment.newInstance()
        profileFragment = ProfileFragment.newInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCurrentFragment(channelsFragment)

        bottomNavigationView = view.findViewById(R.id.bottomNavView)
        bottomNavigationView?.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.channels -> setCurrentFragment(channelsFragment)
                R.id.people -> setCurrentFragment(peopleFragment)
                R.id.profile -> setCurrentFragment(profileFragment)
            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment?) {
        if (fragment != null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.fragmentsContainer, fragment)
                .commit()
        }
    }

    companion object {

        fun newInstance(): Fragment {
            return MainFragment()
        }
    }
}