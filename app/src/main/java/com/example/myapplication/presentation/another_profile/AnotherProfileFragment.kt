package com.example.myapplication.presentation.another_profile

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.common.viewBinding
import com.example.myapplication.databinding.UserProfileBinding
import com.example.myapplication.domain.entity.people.User
import com.example.myapplication.presentation.main.MainActivity
import com.example.myapplication.presentation.main.listener.OnBottomNavigationViewListener

class AnotherProfileFragment : Fragment() {

    private val binding by viewBinding(UserProfileBinding::bind)
    private lateinit var bottomNavigationViewListener: OnBottomNavigationViewListener

    private lateinit var selectedUser: User
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        bottomNavigationViewListener = activity as OnBottomNavigationViewListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_another_profile, container, false)

        actionBar = (activity as MainActivity).supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.title = TITLE

        selectedUser = requireArguments().getParcelable(SELECTED_USER)!!

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Glide.with(this)
            .load(selectedUser.avatar)
            .into(binding.userAvatarInProfile)

        binding.userNameInProfile.text = selectedUser.userName

        when (selectedUser.status) {
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val searchItem: MenuItem = menu.findItem(R.id.search_action)
        searchItem.isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                parentFragmentManager.popBackStack()
                bottomNavigationViewListener.visibilityChanged()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStop() {
        super.onStop()
        actionBar.setDisplayHomeAsUpEnabled(false)
        actionBar.title = "Users..."
    }

    companion object {

        const val TITLE = "Profile"
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