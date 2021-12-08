package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

@SuppressLint("ClickableViewAccessibility")
class TrimView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        color = Color.RED
    }

    private var scroll = false

    private val rect = RectF(0f, 0f, 200f, 200f)

    private val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(
            e1: MotionEvent,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            Log.d(TAG, "onScroll: TrimView")
            if (!scroll) {
                return super.onScroll(e1, e2, distanceX, distanceY)
            }
            with(e2) {
                rect.set(x, 0f, x + 200, y + 200)
                invalidate()
            }
            return super.onScroll(e1, e2, distanceX, distanceY)
        }

        override fun onDown(e: MotionEvent): Boolean {
            scroll = rect.contains(e.x, e.y)
            return super.onDown(e)
        }
    }

    private val gestureDetector = GestureDetector(context, gestureListener)

    init {
        setOnTouchListener { v, event ->
            gestureDetector.onTouchEvent(event)
            true
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(rect, paint)
    }

}