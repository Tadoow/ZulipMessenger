package com.example.myapplication.presentation.emoji_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.common.viewBinding
import com.example.myapplication.databinding.BottomSheetBinding
import com.example.myapplication.domain.entity.chat.Reaction
import com.example.myapplication.presentation.emoji_list.adapter.EmojiAdapter
import com.example.myapplication.presentation.emoji_list.listener.OnEmojiClickListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EmojiDialogFragment : BottomSheetDialogFragment(), OnEmojiClickListener {

    private val binding by viewBinding(BottomSheetBinding::bind)
    private val requestKey by lazy { requireArguments().getString(REQUEST_KEY)!! }
    private lateinit var emojiList: List<Reaction>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        emojiList = requireArguments().getParcelableArrayList(EMOJI_DATA)!!
        return inflater.inflate(R.layout.bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.emojiRecyclerView.layoutManager = GridLayoutManager(activity, 7)

        val emojiAdapter = EmojiAdapter(emojiList, this)
        binding.emojiRecyclerView.adapter = emojiAdapter
    }

    companion object {

        const val TAG = "EmojiDialogFragment"

        private const val REQUEST_KEY = "REQUEST_KEY"
        private const val EMOJI_DATA = "emojiData"

        fun newInstance(
            requestKey: String,
            emojiList: List<Reaction>
        ): EmojiDialogFragment {
            val fragment = EmojiDialogFragment()
            fragment.arguments = Bundle().apply {
                putString(REQUEST_KEY, requestKey)
                putParcelableArrayList(EMOJI_DATA, emojiList as ArrayList)
            }
            return fragment
        }
    }

    override fun emojiClicked(emoji: Reaction) {
        setFragmentResult(requestKey, bundleOf("emoji" to emoji))
    }

}