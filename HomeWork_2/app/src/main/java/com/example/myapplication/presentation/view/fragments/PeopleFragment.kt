package com.example.myapplication.presentation.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.presentation.models.User
import com.example.myapplication.presentation.view.adapters.UsersAdapter
import com.example.myapplication.presentation.view.listeners.OnUserClickListener

class PeopleFragment : Fragment(R.layout.fragment_people) {

    private lateinit var users: List<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(
            TAG,
            "onCreate() called with: savedInstanceState = $savedInstanceState PeopleFragment hashCode ${this.hashCode()}"
        )
        setHasOptionsMenu(true)

        users = requireArguments().getParcelableArrayList(USERS)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(
            TAG,
            "onViewCreated() called with: view = $view, savedInstanceState = $savedInstanceState"
        )

        val usersRecyclerView = view.findViewById<RecyclerView>(R.id.users_recycler_view)
        usersRecyclerView.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        val usersAdapter = UsersAdapter(users, activity as OnUserClickListener)
        usersRecyclerView.adapter = usersAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.search_action)
        val searchView = searchItem.actionView as SearchView
        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {

        const val TAG = "PeopleFragment"
        private const val USERS = "users"

        fun newInstance(users: List<User>): Fragment {
            val fragment = PeopleFragment()
            fragment.arguments = Bundle().apply {
                putParcelableArrayList(USERS, users as ArrayList)
            }
            return fragment
        }
    }
}