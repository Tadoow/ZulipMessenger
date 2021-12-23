package com.example.myapplication.presentation.people

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.common.getFragmentComponent
import com.example.myapplication.common.viewBinding
import com.example.myapplication.data.dto.response.queue.RegisterQueueResponse
import com.example.myapplication.data.mapper.UserPresenceEventToEntityMapper
import com.example.myapplication.data.mapper.UserPresenceFetchedDataToEntityMapper
import com.example.myapplication.databinding.FragmentPeopleBinding
import com.example.myapplication.domain.entity.people.User
import com.example.myapplication.domain.entity.people.UserPresence
import com.example.myapplication.presentation.Factory
import com.example.myapplication.presentation.another_profile.AnotherProfileFragment
import com.example.myapplication.presentation.main.listener.OnBottomNavigationViewListener
import com.example.myapplication.presentation.people.adapter.UsersAdapter
import com.example.myapplication.presentation.people.listener.OnUserClickListener

class PeopleFragment : Fragment(R.layout.fragment_people), OnUserClickListener {

    private val binding by viewBinding(FragmentPeopleBinding::bind)
    private lateinit var bottomNavigationViewListener: OnBottomNavigationViewListener

    private val viewModel: PeopleViewModel by viewModels {
        Factory {
            getFragmentComponent().getPeopleViewModel()
        }
    }

    private val userPresenceFetchedDataToEntityMapper = UserPresenceFetchedDataToEntityMapper()
    private val userPresenceEventToEntityMapper = UserPresenceEventToEntityMapper()

    private lateinit var usersAdapter: UsersAdapter
    private lateinit var usersList: List<User>
    private lateinit var queueId: String
    private var selectedUserId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        bottomNavigationViewListener = activity as OnBottomNavigationViewListener

        if (savedInstanceState == null) {
            Log.d(TAG, "onViewCreated: LoadingUsers")
            viewModel.loadUsers()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            viewModel.registerEventQueue(EVENT_TYPES)
        }

        binding.usersRecyclerView.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        usersAdapter = UsersAdapter(userClickListener = this)
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
                val filteredUsersList = filter(usersList, newText)
                usersAdapter.setItems(filteredUsersList)
                binding.usersRecyclerView.smoothScrollToPosition(0)
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun filter(usersList: List<User>, newText: String?): List<User> {
        if (newText != null) {
            return usersList.filter { it.userName.lowercase().contains(newText.lowercase()) }
        }
        return usersList
    }

    override fun onStart() {
        super.onStart()
        observeLiveData()
        observeEvents()
    }

    private fun observeLiveData() {
        viewModel.progressLiveData.observe(viewLifecycleOwner) { binding.progress.isVisible = it }
        viewModel.usersLiveData.observe(viewLifecycleOwner) {
            usersList = it
            usersAdapter.setItems(usersList)
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
        }
        viewModel.registerQueueLiveData.observe(viewLifecycleOwner) { registerQueue ->
            queueId = registerQueue.id

            setUserPresenceFetchedData(registerQueue)
        }
    }

    private fun observeEvents() {
        viewModel.eventsLiveData.observe(viewLifecycleOwner) { events ->

            val userPresenceEvents = userPresenceEventToEntityMapper(events)
            setUserPresenceEvents(userPresenceEvents)
        }
    }

    private fun setUserPresenceFetchedData(registerQueue: RegisterQueueResponse) {
        val userPresences = userPresenceFetchedDataToEntityMapper(registerQueue)
        userPresences.forEach { usersAdapter.updateUserStatus(it.userId, it.status) }
    }

    private fun setUserPresenceEvents(events: List<UserPresence>) {
        events.forEach { usersAdapter.updateUserStatus(it.userId, it.status) }
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
        viewModel.deleteEventQueue(queueId)
    }

    companion object {

        const val TAG = "PeopleFragment"
        private val EVENT_TYPES = listOf("\"presence\"")

        fun newInstance(): Fragment {
            return PeopleFragment()
        }
    }

    override fun userClicked(user: User) {
        selectedUserId = user.userId
        bottomNavigationViewListener.visibilityChanged()
        parentFragmentManager.beginTransaction()
            .add(R.id.fragments_Container, AnotherProfileFragment.newInstance(user))
            .addToBackStack(null)
            .commit()
    }

}