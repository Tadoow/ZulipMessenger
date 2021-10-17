package com.example.myapplication

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop

class FlexBoxLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    private var resultWidth = 0
    private var resultHeight = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var totalWidth = 0
        var totalHeight = 0
        var currentWidth = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)

            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)
            totalWidth += child.measuredWidth + child.marginLeft + child.marginRight

            if (totalWidth + paddingLeft + paddingRight > MeasureSpec.getSize(widthMeasureSpec)) {

                if (totalHeight + child.measuredHeight +
                    child.marginTop + child.marginBottom <= MeasureSpec.getSize(heightMeasureSpec)
                ) {
                    totalHeight += child.measuredHeight + child.marginTop + child.marginBottom
                }

                currentWidth =
                    maxOf(
                        currentWidth,
                        totalWidth - (child.measuredWidth + child.marginLeft + child.marginRight)
                    )
                totalWidth = child.measuredWidth + child.marginLeft + child.marginRight

            } else {
                totalHeight =
                    maxOf(totalHeight, child.measuredHeight + child.marginTop + child.marginBottom)
            }
        }

        resultWidth = if (currentWidth != 0) {
            maxOf(currentWidth, totalWidth) + paddingLeft + paddingRight
        } else {
            totalWidth + paddingLeft + paddingRight
        }
        resultHeight = totalHeight + paddingTop + paddingBottom

        resultWidth = maxOf(resultWidth, suggestedMinimumWidth)
        resultHeight = maxOf(resultHeight, suggestedMinimumHeight)

        setMeasuredDimension(
            resolveSizeAndState(resultWidth, widthMeasureSpec, 0),
            resolveSizeAndState(resultHeight, heightMeasureSpec, 0)
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var currentTop = 0
        var childLeft = paddingLeft
        var childTop = paddingTop

        val rightBound = width - paddingRight
        val bottomBound = height - paddingBottom

        for (i in 0 until childCount) {
            val child = getChildAt(i)

            when {
                childLeft + child.marginLeft + child.measuredWidth + child.marginRight <= rightBound -> {
                    child.layout(
                        childLeft + child.marginLeft,
                        childTop + child.marginTop,
                        childLeft + child.marginLeft + child.measuredWidth,
                        childTop + child.marginTop + child.measuredHeight
                    )
                    currentTop = child.measuredHeight + child.marginTop + child.marginBottom
                }
                else -> {
                    childTop += currentTop
                    childLeft = paddingLeft
                    if (childTop + child.measuredHeight + child.marginTop + child.marginBottom <= bottomBound) {
                        child.layout(
                            childLeft + child.marginLeft,
                            childTop + child.marginTop,
                            childLeft + child.marginLeft + child.measuredWidth,
                            childTop + child.marginTop + child.measuredHeight
                        )
                        currentTop += child.measuredHeight + child.marginTop + child.marginBottom
                    } else {
                        removeViewAt(childCount - 2)
                    }
                }
            }
            childLeft += child.marginLeft + child.measuredWidth + child.marginRight
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