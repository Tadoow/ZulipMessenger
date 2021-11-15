package com.example.myapplication.ui.chat

import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.common.viewBinding
import com.example.myapplication.databinding.FragmentChatBinding
import com.example.myapplication.domain.channels.entity.ChannelsItem
import com.example.myapplication.domain.channels.entity.Topic
import com.example.myapplication.domain.chat.entity.ChatItem
import com.example.myapplication.domain.chat.entity.IncomeMessage
import com.example.myapplication.domain.chat.entity.OutcomeMessage
import com.example.myapplication.domain.chat.entity.Reaction
import com.example.myapplication.ui.chat.adapter.ChatAdapter
import com.example.myapplication.ui.chat.listener.OnMessageClickListener
import com.example.myapplication.ui.chat.listener.OnReactionClickListener
import com.example.myapplication.ui.emoji_list.EmojiDialogFragment
import com.example.myapplication.ui.main.MainActivity

class ChatFragment : Fragment(), OnMessageClickListener, OnReactionClickListener {

    private val binding by viewBinding(FragmentChatBinding::bind)

    private lateinit var chatItems: List<ChatItem>
    private lateinit var topicName: String
    private lateinit var emojiDialogFragment: EmojiDialogFragment
    private lateinit var chatAdapter: ChatAdapter
    private var selectedMessage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called with: savedInstanceState = $savedInstanceState")
        childFragmentManager.setFragmentResultListener(REQUEST_KEY, this) { requestKey, bundle ->
            val selectedEmoji = bundle.getString("emoji")!!
            emojiInDialogClicked(selectedEmoji)
        }
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
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        chatItems = requireArguments().getParcelableArrayList(CHAT_ITEMS)!!
        topicName = requireArguments().getString(TOPIC_NAME)!!
        val actionBarColor = requireArguments().getInt(TOPIC_BACKGROUND_COLOR)
        val streamHostName = requireArguments().getString(STREAM_HOST_NAME)

        val actionBar = (activity as MainActivity).supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setBackgroundDrawable(ColorDrawable(actionBarColor))
        actionBar.title = streamHostName

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(
            TAG,
            "onViewCreated() called with: view = $view, savedInstanceState = $savedInstanceState"
        )
        binding.topicName.append(topicName.lowercase())

        binding.chatRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        chatAdapter = ChatAdapter(chatItems, this, this)
        binding.chatRecyclerView.adapter = chatAdapter

        binding.sendButton.setOnClickListener {
            val newMessage = OutcomeMessage(binding.newMessageEditText.text.toString(), emptyList())
            chatAdapter.addItem(newMessage)

            binding.newMessageEditText.text.clear()
            hideKeyboard()
            binding.chatRecyclerView.scrollToPosition(chatItems.size - 1)
        }

        emojiDialogFragment = EmojiDialogFragment.newInstance(REQUEST_KEY)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> parentFragmentManager.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun hideKeyboard() {
        if (activity?.currentFocus == null) {
            return
        }
        val inputMethodManager =
            activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
    }

    override fun addEmojiClicked(messagePosition: Int) {
        selectedMessage = messagePosition
        emojiDialogFragment.show(childFragmentManager, EmojiDialogFragment.TAG)
    }

    override fun messageLongClicked(messagePosition: Int): Boolean {
        selectedMessage = messagePosition
        emojiDialogFragment.show(childFragmentManager, EmojiDialogFragment.TAG)
        return true
    }

    override fun reactionInMessageClicked(reaction: Reaction, messagePosition: Int) {
        reaction.reactionClickState = !reaction.reactionClickState
        reaction.userId =
            if (reaction.userId.contains(LOCAL_USER_ID)) {
                reaction.userId - LOCAL_USER_ID
            } else {
                reaction.userId + LOCAL_USER_ID
            }
        chatAdapter.notifyItemChanged(messagePosition)
    }

    private fun emojiInDialogClicked(emoji: String) {
        when (val message = chatItems[selectedMessage]) {
            is IncomeMessage -> message.reactions =
                message.reactions + Reaction(emoji, mutableListOf(LOCAL_USER_ID))
            is OutcomeMessage -> message.reactions =
                message.reactions + Reaction(emoji, mutableListOf(LOCAL_USER_ID))
        }
        chatAdapter.notifyItemChanged(selectedMessage)
        emojiDialogFragment.dismiss()
    }

    companion object {

        const val TAG = "ChatFragment"

        private const val LOCAL_USER_ID = 1
        private const val REQUEST_KEY = "chatFragment"

        private const val CHAT_ITEMS = "chatItems"
        private const val TOPIC_NAME = "topicName"
        private const val STREAM_HOST_NAME = "streamHostName"
        private const val TOPIC_BACKGROUND_COLOR = "topicBackgroundColor"

        fun newInstance(channelsItem: ChannelsItem): Fragment {
            val topic = channelsItem as Topic
            val fragment = ChatFragment()
            val arguments = Bundle().apply {
                putParcelableArrayList(CHAT_ITEMS, topic.chatItems as ArrayList)
                putString(TOPIC_NAME, topic.topicName)
                putString(STREAM_HOST_NAME, topic.streamHostName)
                putInt(TOPIC_BACKGROUND_COLOR, topic.backgroundColor)
            }
            fragment.arguments = arguments
            return fragment
        }
    }
}