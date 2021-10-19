package com.example.myapplication.view

import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.view.customViewGroup.FlexBoxLayout

interface OnAddSmileViewListener {
    fun addSmileViewClicked(customAddView: ImageView?, flexBoxLayout: FlexBoxLayout?)
    fun messageViewClicked(messageTextView: TextView?)
}