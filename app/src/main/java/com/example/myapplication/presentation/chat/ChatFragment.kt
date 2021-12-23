package com.example.myapplication.presentation.chat

import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.common.getFragmentComponent
import com.example.myapplication.common.viewBinding
import com.example.myapplication.data.dto.event.MessageEvent
import com.example.myapplication.data.dto.event.ReactionEvent
import com.example.myapplication.data.dto.response.Response
import com.example.myapplication.data.mapper.MessageDtoToEntityMapper
import com.example.myapplication.data.repository.RepositoryImpl
import com.example.myapplication.databinding.FragmentChatBinding
import com.example.myapplication.domain.entity.channels.Topic
import com.example.myapplication.domain.entity.chat.*
import com.example.myapplication.presentation.Factory
import com.example.myapplication.presentation.chat.adapter.ChatAdapter
import com.example.myapplication.presentation.chat.listener.OnMessageClickListener
import com.example.myapplication.presentation.chat.listener.OnReactionClickListener
import com.example.myapplication.presentation.emoji_list.EmojiDialogFragment
import com.example.myapplication.presentation.main.MainActivity
import com.example.myapplication.presentation.main.listener.OnBackPressedListener
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException

class ChatFragment : Fragment(), OnMessageClickListener, OnReactionClickListener {

    private val binding by viewBinding(FragmentChatBinding::bind)
    private val viewModel: ChatViewModel by viewModels {
        Factory {
            getFragmentComponent().getChatViewModel()
        }
    }

    private lateinit var chatItems: List<ChatItem>
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var selectedTopic: Topic
    private var selectedMessage = 0
    private lateinit var emojiDialogFragment: EmojiDialogFragment
    private lateinit var queueId: String

    private val messageDtoToEntityMapper: MessageDtoToEntityMapper = MessageDtoToEntityMapper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called with: savedInstanceState = $savedInstanceState")

        childFragmentManager.setFragmentResultListener(REQUEST_KEY, this) { _, bundle ->
            val selectedEmoji = bundle.getParcelable<Reaction>("emoji")!!
            emojiReactionInDialogClicked(selectedEmoji)
        }
        setHasOptionsMenu(true)

        viewModel.emojiListLiveData.observe(this) {
            emojiDialogFragment = EmojiDialogFragment.newInstance(REQUEST_KEY, it)
        }
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

        selectedTopic = requireArguments().getParcelable(TOPIC_NAME)!!
        val actionBarColor = selectedTopic.backgroundColor
        val streamHostName = selectedTopic.streamHostName

        val actionBar = (activity as MainActivity).supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setBackgroundDrawable(ColorDrawable(actionBarColor))
        actionBar.title = resources.getString(R.string.stream_name, streamHostName)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            viewModel.loadMessagesInTopic(selectedTopic)
            val narrow = listOf(
                listOf("\"stream\"", "\"${selectedTopic.streamHostName}\""),
                listOf("\"topic\"", "\"${selectedTopic.topicName}\"")
            ).toString()
            viewModel.registerEventQueue(EVENT_TYPES, narrow)
        }

        binding.topicName.append(selectedTopic.topicName.lowercase())

        binding.chatRecyclerView.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        chatAdapter = ChatAdapter(messageClickListener = this, reactionClickListener = this)
        binding.chatRecyclerView.adapter = chatAdapter

        binding.sendButton.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage() {
        val messageText = binding.newMessageEditText.text.toString()

        viewModel.sendMessageToTopic(selectedTopic, messageText)

        binding.newMessageEditText.text.clear()
        hideKeyboard()
    }

    private fun hideKeyboard() {
        if (activity?.currentFocus == null) {
            return
        }
        val inputMethodManager =
            activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
    }

    override fun onStart() {
        super.onStart()
        observeLiveData()
        observeEvents()
    }

    private fun observeLiveData() {
        viewModel.progressLiveData.observe(viewLifecycleOwner) { binding.progress.isVisible = it }
        viewModel.chatItemsLiveData.observe(viewLifecycleOwner) {
            chatItems = it
            chatAdapter.setItems(chatItems)
            binding.chatRecyclerView.scrollToPosition(chatItems.lastIndex)
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val error = if (it is HttpException) {
                Json.decodeFromString<Response>(it.response()?.errorBody()?.string()!!).msg
            } else {
                it.message
            }
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
        viewModel.registerQueueLiveData.observe(viewLifecycleOwner) { registerQueue ->
            queueId = registerQueue.id
        }
    }

    private fun observeEvents() {
        viewModel.eventsLiveData.observe(viewLifecycleOwner) { events ->
            events.forEach { event ->
                when (event) {
                    is MessageEvent -> handleMessageEvent(event)
                    is ReactionEvent -> handleReactionEvent(event)
                    else -> throw IllegalArgumentException("No such eventType $event")
                }
            }
        }
    }

    private fun handleMessageEvent(event: MessageEvent) {
        val items = messageDtoToEntityMapper(event.message)
        if (chatItems.contains(items.first())) {
            chatAdapter.addItem(items.last())
        } else {
            chatAdapter.addItems(items)
        }
        binding.chatRecyclerView.smoothScrollToPosition(chatItems.lastIndex)
    }

    private fun handleReactionEvent(event: ReactionEvent) {
        val targetMessage =
            chatItems.find { item -> item is Message && item.id == event.messageId }
        val messageIndex = chatItems.indexOf(targetMessage)
        val reactionFromEvent =
            Reaction(event.emojiName, event.emojiCode, listOf(event.userId))
        val reactionInMessage =
            (targetMessage as Message).emojiList.find { it.emojiName == reactionFromEvent.emojiName }
        if (reactionInMessage == null) {
            targetMessage.emojiList += reactionFromEvent
        } else {
            if (event.userId == OWN_USER_ID) {
                reactionInMessage.reactionClickState =
                    !reactionInMessage.reactionClickState
            }
            if (event.op == "add") {
                reactionInMessage.reactedUsersIds += event.userId
            } else {
                reactionInMessage.reactedUsersIds -= event.userId
                if (reactionInMessage.reactedUsersIds.isEmpty()) {
                    targetMessage.emojiList -= reactionInMessage
                }
            }
        }
        chatAdapter.notifyItemChanged(messageIndex)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> parentFragmentManager.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
        (activity as OnBackPressedListener).backPressedInChat(queueId)
    }

    override fun messageLongClicked(messagePosition: Int): Boolean {
        selectedMessage = messagePosition
        emojiDialogFragment.show(childFragmentManager, EmojiDialogFragment.TAG)
        return true
    }

    override fun reactionInMessageClicked(emoji: Reaction, messagePosition: Int) {
        val message = chatItems[messagePosition] as Message
        if (emoji.reactedUsersIds.contains(OWN_USER_ID)) {
            viewModel.removeEmojiReaction(message.id, emoji)
        } else {
            viewModel.addEmojiReaction(message.id, emoji)
        }
    }

    override fun reactionAppendClicked(messagePosition: Int) {
        selectedMessage = messagePosition
        emojiDialogFragment.show(childFragmentManager, EmojiDialogFragment.TAG)
    }

    private fun emojiReactionInDialogClicked(emoji: Reaction) {
        when (val message = chatItems[selectedMessage] as Message) {
            is IncomeMessage -> addEmojiReactionFromDialog(emoji, message)
            is OutcomeMessage -> addEmojiReactionFromDialog(emoji, message)
        }
        emojiDialogFragment.dismiss()
    }

    private fun addEmojiReactionFromDialog(emoji: Reaction, message: Message) {
        val emojiInMessage = message.emojiList.find { it == emoji }
        if (emojiInMessage == null || !emojiInMessage.reactedUsersIds.contains(OWN_USER_ID)) {
            viewModel.addEmojiReaction(message.id, emoji)
        }
    }

    companion object {

        const val TAG = "ChatFragment"

        private val OWN_USER_ID = RepositoryImpl.owner.userId
        private val EVENT_TYPES = listOf("\"message\"", "\"reaction\"")
        private const val REQUEST_KEY = "chatFragment"

        private const val TOPIC_NAME = "topicName"

        fun newInstance(topic: Topic): Fragment {
            val fragment = ChatFragment()
            val arguments = Bundle().apply {
                putParcelable(TOPIC_NAME, topic)
            }
            fragment.arguments = arguments
            return fragment
        }
    }

}