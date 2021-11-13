package com.example.myapplication.ui.people

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MessengerApplication
import com.example.myapplication.R
import com.example.myapplication.common.viewBinding
import com.example.myapplication.databinding.FragmentPeopleBinding
import com.example.myapplication.ui.people.adapter.UsersAdapter
import com.example.myapplication.ui.people.listener.OnUserClickListener

class PeopleFragment : Fragment(R.layout.fragment_people) {

    private val binding by viewBinding(FragmentPeopleBinding::bind)

    private val viewModel: PeopleViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PeopleViewModel(MessengerApplication.repository) as T
            }
        }
    }

    private lateinit var usersAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(
            TAG,
            "onCreate() called with: savedInstanceState = $savedInstanceState PeopleFragment hashCode ${this.hashCode()}"
        )
        setHasOptionsMenu(true)

        viewModel.loadUsers()
    }

    private fun observeLiveData() {
        viewModel.progressLiveData.observe(viewLifecycleOwner) { binding.progress.isVisible = it }
        viewModel.listItemsLiveData.observe(viewLifecycleOwner) { usersAdapter.setItems(it) }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(
            TAG,
            "onViewCreated() called with: view = $view, savedInstanceState = $savedInstanceState"
        )
        observeLiveData()

        binding.usersRecyclerView.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        usersAdapter = UsersAdapter(userClickListener = activity as OnUserClickListener)
        binding.usersRecyclerView.adapter = usersAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.search_action)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    viewModel.searchUser(newText)
                    Log.d(TAG, "onQueryTextChange: $newText")
                }
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {

        const val TAG = "PeopleFragment"

        fun newInstance(): Fragment {
            return PeopleFragment()
        }
    }
}