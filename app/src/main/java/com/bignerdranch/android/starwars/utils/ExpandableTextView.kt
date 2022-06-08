package com.bignerdranch.android.starwars.utils
import android.R
import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.ViewTreeObserver
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat

class ExpandableTextView : AppCompatTextView {
    private val readMoreText = "... Show More"
    private var readMoreColor: Int = ContextCompat.getColor(context, R.color.white)
    private var _maxLines = 2
    private var originalText: CharSequence? = null
    private var isTruncate = false
    var truncateListener: (() -> Unit)? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        val vto = viewTreeObserver
        vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val obs = viewTreeObserver
                obs.removeGlobalOnLayoutListener(this)
                truncateText()
            }
        })
        setOnClickListener {
            if (isTruncate) {
                if(truncateListener != null) truncateListener?.invoke()
                else unTruncateText()
            } else {
                truncateText()
            }
        }
    }

    override fun setText(text: CharSequence, type: BufferType) {
        super.setText(text, type)
        if (originalText == null) {
            originalText = text
        }
    }

    override fun getMaxLines(): Int {
        return _maxLines
    }

    override fun setMaxLines(maxLines: Int) {
        _maxLines = maxLines
    }

    fun truncateText() {
        val maxLines = _maxLines
        if (lineCount >= maxLines) {
            isTruncate = true
            val lineEndIndex = layout.getLineEnd(maxLines - 1)
            originalText = text
            val truncatedText = text.subSequence(0, lineEndIndex - readMoreText.length + 1).toString() + readMoreText
            val spannable: Spannable = SpannableString(truncatedText)
            spannable.setSpan(
                ForegroundColorSpan(readMoreColor),
                truncatedText.length - readMoreText.length,
                truncatedText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setText(spannable, BufferType.SPANNABLE)
            super.setMaxLines(_maxLines)
        } else {
            isTruncate = false
        }
    }

    fun unTruncateText() {
        isTruncate = false
        val maxLines = 50
        setText(originalText.toString(), BufferType.SPANNABLE)
        super.setMaxLines(maxLines)
    }

    fun reset() {
        originalText = null
    }
}