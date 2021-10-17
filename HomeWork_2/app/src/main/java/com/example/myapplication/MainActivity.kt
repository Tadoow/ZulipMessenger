package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val customAddView = findViewById<ImageView>(R.id.customSmileAdd)
        val flexBoxLayout = findViewById<FlexBoxLayout>(R.id.flexBoxLayout)

        customAddView.setOnClickListener {
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
            flexBoxLayout.addView(smileView, flexBoxLayout.size - 1, layoutParams)
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