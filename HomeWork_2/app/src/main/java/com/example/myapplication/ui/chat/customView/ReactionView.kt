package com.example.myapplication.ui.chat.customView

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.myapplication.R
import kotlin.math.max

class ReactionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    var reactionUnicode = ""
        set(value) {
            field = value
            invalidate()
        }
    var reactionCount = 0
        set(value) {
            field = value
            requestLayout()
        }

    private val smilePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        color = Color.LTGRAY
        textSize = 42f
    }

    private val textBounds = Rect()
    private val textCoordinate = PointF()

    init {
        val typedArray: TypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.ReactionView,
            defStyleAttr,
            defStyleRes
        )

        try {
            reactionUnicode = typedArray.getString(R.styleable.ReactionView_reactionSrc)
                ?: ""
            reactionCount = typedArray.getInteger(R.styleable.ReactionView_reactionCount, 0)
        } finally {
            typedArray.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val smileViewText = reactionUnicode + reactionCount
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
        Log.d(
            "ReactionView",
            "onSizeChanged() called with: w = $w, h = $h, oldw = $oldw, oldh = $oldh"
        )
        textCoordinate.x = w / 2f
        textCoordinate.y = h / 2f + textBounds.height() / 3
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + SUPPORTED_DRAWABLE_STATE.size)
        if (isSelected) {
            mergeDrawableStates(drawableState, SUPPORTED_DRAWABLE_STATE)
        }
        return drawableState
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawText(
            reactionUnicode + reactionCount,
            textCoordinate.x,
            textCoordinate.y,
            smilePaint
        )
    }

    companion object {
        private val SUPPORTED_DRAWABLE_STATE = intArrayOf(android.R.attr.state_selected)
    }
}