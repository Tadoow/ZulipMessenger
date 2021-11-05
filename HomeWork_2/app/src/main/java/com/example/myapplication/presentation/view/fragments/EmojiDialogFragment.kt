package com.example.myapplication.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.presentation.view.adapters.EmojiAdapter
import com.example.myapplication.presentation.view.listeners.OnEmojiClickListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EmojiDialogFragment : BottomSheetDialogFragment(), OnEmojiClickListener {

    private val requestKey by lazy { requireArguments().getString(REQUEST_KEY)!! }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val emojiRecyclerView: RecyclerView = view.findViewById(R.id.emoji_recycler_view)
        emojiRecyclerView.layoutManager = GridLayoutManager(activity, 7)

        val emojiList = resources.getStringArray(R.array.emoji).toList()
        val emojiAdapter = EmojiAdapter(emojiList, this)
        emojiRecyclerView.adapter = emojiAdapter
    }

    companion object {

        const val TAG = "EmojiDialogFragment"

        private const val REQUEST_KEY = "REQUEST_KEY"

        fun newInstance(requestKey: String): EmojiDialogFragment {
            val fragment = EmojiDialogFragment()
            fragment.arguments = Bundle().apply {
                putString(REQUEST_KEY, requestKey)
            }
            return fragment
        }
    }

    override fun emojiClicked(emoji: String) {
        setFragmentResult(requestKey, bundleOf("emoji" to emoji))
    }
}