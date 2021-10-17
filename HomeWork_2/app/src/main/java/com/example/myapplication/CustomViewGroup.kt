package com.example.myapplication

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import kotlin.math.max

class CustomViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    init {
        inflate(context, R.layout.custom_view_group_layout, this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val iconView = getChildAt(0)
        val messageView = getChildAt(1)
        val smileView = getChildAt(2)

        var totalWidth = 0
        var totalHeight = 0

        measureChildWithMargins(iconView, widthMeasureSpec, 0, heightMeasureSpec, 0)

        totalWidth += iconView.measuredWidth + iconView.marginLeft + iconView.marginRight
        totalHeight =
            maxOf(totalHeight, iconView.measuredHeight + iconView.marginTop + iconView.marginBottom)

        measureChildWithMargins(messageView, widthMeasureSpec, totalWidth, heightMeasureSpec, 0)

        totalWidth += messageView.measuredWidth + messageView.marginLeft + messageView.marginRight
        totalHeight =
            maxOf(
                totalHeight,
                messageView.measuredHeight + messageView.marginTop + messageView.marginBottom
            )

        measureChildWithMargins(
            smileView,
            widthMeasureSpec,
            iconView.measuredWidth + iconView.marginLeft + iconView.marginRight,
            heightMeasureSpec,
            totalHeight
        )

        totalWidth += smileView.measuredWidth + smileView.marginLeft + smileView.marginRight
        totalHeight += smileView.measuredHeight + smileView.marginTop + smileView.marginBottom

        var resultWidth = totalWidth + paddingLeft + paddingRight
        var resultHeight = totalHeight + paddingTop + paddingBottom

        resultWidth = max(resultWidth, suggestedMinimumWidth)
        resultHeight = max(resultHeight, suggestedMinimumHeight)

        setMeasuredDimension(
            resolveSizeAndState(resultWidth, widthMeasureSpec, 0),
            resolveSizeAndState(resultHeight, heightMeasureSpec, 0)
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val iconView = getChildAt(0)
        val messageView = getChildAt(1)
        val smileView = getChildAt(2)

        iconView.layout(
            paddingLeft + iconView.marginLeft,
            paddingTop + iconView.marginTop,
            paddingLeft + iconView.marginLeft + iconView.measuredWidth,
            paddingTop + iconView.marginTop + iconView.measuredHeight
        )

        messageView.layout(
            iconView.right + iconView.marginRight + messageView.marginLeft,
            paddingTop + messageView.marginTop,
            iconView.right + iconView.marginRight + messageView.marginLeft + messageView.measuredWidth,
            paddingTop + messageView.marginTop + messageView.measuredHeight
        )

        smileView.layout(
            messageView.left,
            messageView.bottom + messageView.marginBottom + smileView.marginTop,
            messageView.left + smileView.measuredWidth,
            messageView.bottom + messageView.marginBottom + smileView.marginTop + smileView.measuredHeight
        )
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun checkLayoutParams(p: LayoutParams?): Boolean {
        return p is MarginLayoutParams
    }

    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
        return MarginLayoutParams(p)
    }
}