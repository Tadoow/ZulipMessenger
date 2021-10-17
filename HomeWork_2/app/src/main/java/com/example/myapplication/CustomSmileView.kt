package com.example.myapplication

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.max

class CustomSmileView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    var smileUnicode = ""
        set(value) {
            field = value
            invalidate()
        }
    var smileCount = ""
        set(value) {
            field = value
            requestLayout()
        }

    private val smilePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        color = Color.LTGRAY
        textSize = 40f
    }

    private val textBounds = Rect()
    private val textCoordinate = PointF()

    init {
        val typedArray: TypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CustomSmileView,
            defStyleAttr,
            defStyleRes
        )

        try {
            smileUnicode = typedArray.getString(R.styleable.CustomSmileView_customSmileSrc)
                ?: ""
            smileCount = typedArray.getString(R.styleable.CustomSmileView_customSmileCount) ?: ""
        } finally {
            typedArray.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val smileViewText = smileUnicode + smileCount
        smilePaint.getTextBounds(smileViewText, 0, smileViewText.length, textBounds)

        val textHeight = textBounds.height()
        val textWidth = textBounds.width()

        val totalWidth = textWidth + paddingRight + paddingLeft
        val totalHeight = textHeight + paddingTop + paddingBottom

        val resultWidth = max(totalWidth, suggestedMinimumWidth)
        val resultHeight = max(totalHeight, suggestedMinimumHeight)

        setMeasuredDimension(
            resolveSizeAndState(resultWidth, widthMeasureSpec, 0),
            resolveSizeAndState(resultHeight, heightMeasureSpec, 0)
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        textCoordinate.x = w / 2f
        textCoordinate.y = h / 2f + textBounds.height() / 2
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + SUPPORTED_DRAWABLE_STATE.size)
        if (isSelected) {
            mergeDrawableStates(drawableState, SUPPORTED_DRAWABLE_STATE)
        }
        return drawableState
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawText(smileUnicode + smileCount, textCoordinate.x, textCoordinate.y, smilePaint)
    }

    companion object {
        private val SUPPORTED_DRAWABLE_STATE = intArrayOf(android.R.attr.state_selected)
    }
}