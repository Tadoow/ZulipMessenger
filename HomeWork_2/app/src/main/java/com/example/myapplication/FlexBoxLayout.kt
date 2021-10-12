package com.example.myapplication

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import kotlin.math.max

class FlexBoxLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        var totalWidth = 0
        var totalHeight = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)

            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)

            totalWidth += child.measuredWidth + child.marginLeft + child.marginRight
            totalHeight = max(totalHeight, child.measuredHeight + child.marginTop + child.marginBottom)
        }

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
        var currentRight = paddingLeft
        var currentBottom = 0
        val rightBound = width - paddingRight
        val bottomBound = height - paddingBottom
        for (i in 0 until childCount) {
            val child = getChildAt(i)

            when {
                currentBottom + child.measuredHeight > bottomBound -> println("Не добавляем вьюшку")
                currentRight + child.measuredWidth < rightBound -> child.layout(
                    currentRight + child.marginLeft,
                    currentBottom + paddingTop + child.marginTop,
                    currentRight + child.measuredWidth,
                    currentBottom + paddingTop + child.marginTop + child.measuredHeight
                )
                else -> {
                    currentRight = paddingLeft
                    currentBottom += child.measuredHeight
                    child.layout(
                        currentRight + child.marginLeft,
                        currentBottom + paddingTop + child.marginTop,
                        currentRight + child.measuredWidth,
                        currentBottom + paddingTop + child.marginTop + child.measuredHeight
                    )
                }
            }

            currentRight += child.marginLeft + child.measuredWidth + child.marginRight
        }
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