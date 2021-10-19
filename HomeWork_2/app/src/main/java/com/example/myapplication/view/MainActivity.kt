package com.example.myapplication.view

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.DateMessage
import com.example.myapplication.model.IncomeMessage
import com.example.myapplication.model.Message
import com.example.myapplication.model.OutcomeMessage
import com.example.myapplication.view.adapter.EmojiAdapter
import com.example.myapplication.view.adapter.MyAdapter
import com.example.myapplication.view.customView.CustomSmileView
import com.example.myapplication.view.customViewGroup.FlexBoxLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*

class MainActivity : AppCompatActivity(), OnAddSmileViewListener {

    private val messages = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val adapter = MyAdapter(makeMessagesList(), this)
        recyclerView.adapter = adapter

        val sendButton = findViewById<ImageButton>(R.id.sendButton)
        val messageEditText = findViewById<EditText>(R.id.messageEditText)
        sendButton.setOnClickListener {
            messages.add(OutcomeMessage(messageEditText.text.toString()))
            messageEditText.text = null
            val keyboard = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            adapter.setItems(messages)
            recyclerView.scrollToPosition(messages.size - 1)
        }
    }

    private fun makeMessagesList(): List<Message> {
        messages.add(DateMessage().getCurrentDate(3))
        messages.add(OutcomeMessage("Привет! Lorem ipsum test message dalshe zabil"))
        messages.add(DateMessage().getCurrentDate())
        messages.add(
            IncomeMessage(
                "Darrell Steward",
                "Привет! Lorem ipsum test message dalshe zabil"
            )
        )
        messages.add(IncomeMessage("Darrell Steward", "Привет!"))
        messages.add(OutcomeMessage("Привет!"))
        return messages
    }

    override fun addSmileViewClicked(customAddView: ImageView?, flexBoxLayout: FlexBoxLayout?) {
        customAddView?.setOnClickListener {
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(0, 10, 30, 10)
            val smileView = CustomSmileView(this).apply {
                smileUnicode = getString(R.string.customSmile)
                background = getDrawable(R.drawable.custom_smile_view_states)
                setPadding(35, 25, 35, 25)
            }
            flexBoxLayout?.addView(smileView, flexBoxLayout.size - 1, layoutParams)
            smileView.setOnClickListener { v ->
                if (v is CustomSmileView) {
                    v.isSelected = !v.isSelected
                    if (v.isSelected) {
                        v.smileCount = "1"
                    } else {
                        v.smileCount = ""
                    }
                }
            }
        }
    }

    override fun messageViewClicked(messageTextView: TextView?) {
        showBottomSheetDialog()
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet)

        val emojiRecyclerView = bottomSheetDialog.findViewById<RecyclerView>(R.id.emojiRecyclerView)
        emojiRecyclerView?.layoutManager = GridLayoutManager(this, 7)

        val emojiList = resources.getStringArray(R.array.emoji).toMutableList()
        val emojiAdapter = EmojiAdapter(emojiList)
        emojiRecyclerView?.adapter = emojiAdapter

        bottomSheetDialog.show()
    }
}