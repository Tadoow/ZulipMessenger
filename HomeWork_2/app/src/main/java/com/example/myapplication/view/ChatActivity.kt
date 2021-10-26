package com.example.myapplication.view

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.*
import com.example.myapplication.view.adapters.EmojiAdapter
import com.example.myapplication.view.adapters.MessageAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog

class ChatActivity : AppCompatActivity(), OnMessageClickListener, OnReactionClickListener {

    private val messages = mutableListOf<Message>()
    private var bottomSheetDialog: BottomSheetDialog? = null
    private var messagesAdapter: MessageAdapter? = null
    private var selectedMessage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        messagesAdapter = MessageAdapter(backEndEmulation(), this, this)
        recyclerView.adapter = messagesAdapter

        val sendButton = findViewById<ImageButton>(R.id.sendButton)
        val messageEditText = findViewById<EditText>(R.id.messageEditText)
        sendButton.setOnClickListener {
            val newMessage = OutcomeMessage(messageEditText.text.toString(), emptyList())
            val keyboard = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            messageEditText.text = null
            messagesAdapter?.addItem(newMessage)
            recyclerView.scrollToPosition(messages.size - 1)
        }

        createBottomSheetDialog()
    }

    private fun backEndEmulation(): List<Message> {
        val firstMessageReactions = mutableListOf(
            Reaction("🥰", mutableListOf(4, 3, 6)),
            Reaction("☺", mutableListOf(5, 3))
        )
        val secondMessageReactions = mutableListOf(
            Reaction("😍", mutableListOf(4, 9)),
            Reaction("😳", mutableListOf(5, 3)),
            Reaction("😛", mutableListOf(7, 2))
        )
        messages.add(DateMessage().getCurrentDate(3))
        messages.add(
            OutcomeMessage(
                "Привет! Lorem ipsum test message dalshe zabil",
                firstMessageReactions
            )
        )
        messages.add(DateMessage().getCurrentDate())
        messages.add(
            IncomeMessage(
                "Darrell Steward",
                "Привет! Lorem ipsum test message dalshe zabil",
                secondMessageReactions
            )
        )
        messages.add(
            IncomeMessage(
                "Darrell Steward",
                "Привет!",
                emptyList()
            )
        )
        messages.add(OutcomeMessage("Привет!", emptyList()))
        messages.add(OutcomeMessage("ААааааааааааа", emptyList()))
        return messages
    }

    override fun addEmojiViewClicked(messagePosition: Int) {
        selectedMessage = messagePosition
        bottomSheetDialog?.show()
    }

    override fun messageViewClicked(messagePosition: Int): Boolean {
        selectedMessage = messagePosition
        bottomSheetDialog?.show()
        return true
    }

    private fun createBottomSheetDialog() {
        bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog?.setContentView(R.layout.bottom_sheet)
        val emojiRecyclerView =
            bottomSheetDialog?.findViewById<RecyclerView>(R.id.emojiRecyclerView)
        emojiRecyclerView?.layoutManager = GridLayoutManager(this, 7)

        val emojiList = resources.getStringArray(R.array.emoji).toMutableList()
        val emojiAdapter = EmojiAdapter(emojiList, this)
        emojiRecyclerView?.adapter = emojiAdapter
    }

    override fun reactionClicked(reaction: Reaction, messagePosition: Int) {
        reaction.reactionClickState = !reaction.reactionClickState
        reaction.user_id =
            if (reaction.user_id.contains(1)) reaction.user_id - 1 else reaction.user_id + 1
        Log.d(
            "TAG",
            "addReactionsViews() called with: reactionCount = ${reaction.getClicksCount()}"
        )
        messagesAdapter?.notifyItemChanged(messagePosition)
    }

    override fun emojiClicked(emoji: String) {
        val message = messages[selectedMessage]
        message.reactions = message.reactions + Reaction(emoji, mutableListOf(1))
        messagesAdapter?.notifyItemChanged(selectedMessage)
        bottomSheetDialog?.dismiss()
    }
}