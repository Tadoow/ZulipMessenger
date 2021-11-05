package com.example.myapplication.presentation.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.presentation.models.User
import com.example.myapplication.presentation.view.MainActivity

class AnotherProfileFragment : Fragment() {

    private lateinit var selectedUser: User
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called with: savedInstanceState = $savedInstanceState")
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(
            TAG,
            "onCreateView() called with: inflater = $inflater, container = $container, savedInstanceState = $savedInstanceState"
        )
        val view = inflater.inflate(R.layout.fragment_another_profile, container, false)

        actionBar = (activity as MainActivity).supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.title = "Profile"

        selectedUser = requireArguments().getParcelable(SELECTED_USER)!!

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(
            TAG,
            "onViewCreated() called with: view = $view, savedInstanceState = $savedInstanceState"
        )
        val userName = view.findViewById<TextView>(R.id.user_name_in_profile)
        userName.text = selectedUser.userName

        val userStatus = view.findViewById<TextView>(R.id.user_status)
        if (selectedUser.online) {
            userStatus.apply {
                setTextColor(ResourcesCompat.getColor(resources, R.color.light_green, null))
                text = resources.getString(R.string.user_online)
            }
        } else {
            userStatus.apply {
                setTextColor(ResourcesCompat.getColor(resources, R.color.red, null))
                text = resources.getString(R.string.user_offline)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val searchItem: MenuItem = menu.findItem(R.id.search_action)
        searchItem.isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> parentFragmentManager.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
        actionBar.setDisplayHomeAsUpEnabled(false)
        actionBar.title = "Users..."
    }

    companion object {

        const val TAG = "AnotherProfileFragment"

        private const val SELECTED_USER = "selectedUser"

        fun newInstance(user: User): Fragment {
            val fragment = AnotherProfileFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(SELECTED_USER, user)
            }
            return fragment
        }
    }

}