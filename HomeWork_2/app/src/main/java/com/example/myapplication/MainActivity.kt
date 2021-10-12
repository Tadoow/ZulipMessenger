package com.example.myapplication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginRight
import androidx.core.view.setPadding
import androidx.core.view.size

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val customTextView = findViewById<CustomSmileView>(R.id.customSmileView)
//        customTextView.setOnClickListener {
//            customTextView.isSelected = !customTextView.isSelected
//            if (customTextView.isSelected) {
//                customTextView.smileCount = "1"
//            } else {
//                customTextView.smileCount = ""
//            }
//        }

        val customAddView = findViewById<CustomSmileView>(R.id.customSmileAdd)
        val flexBoxLayout = findViewById<FlexBoxLayout>(R.id.flexBoxLayout)
        customAddView.setOnClickListener {
            val smileView = CustomSmileView(this).apply {
                smileUnicode = getString(R.string.customSmile)
                background = getDrawable(R.drawable.custom_smile_view_states)
                setPadding(40)
            }
            flexBoxLayout.addView(smileView, flexBoxLayout.size - 1)
            smileView.setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {
        val view = v as CustomSmileView
        view.isSelected = !view.isSelected
        if (view.isSelected) {
            view.smileCount = "1"
        } else {
            view.smileCount = ""
        }
    }
}