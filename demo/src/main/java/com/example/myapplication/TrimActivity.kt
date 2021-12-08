package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.SeekBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.setup
import com.example.myapplication.databinding.ActivityTrimBinding
import kotlin.math.roundToInt
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams


const val TAG = "demo_tag"

class TrimActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTrimBinding

    private val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(
            e1: MotionEvent,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            val x = e2.x
            val y = e2.y
            Log.d(TAG, "x = $x")
            Log.d(TAG, "y = $y")
            setMargins(binding.trimStart, x.toInt(), 0, 0, 0)
//            binding.myView.scrollTo(x.toInt(), binding.myView.y.toInt())
//            val layoutParams =
//                ConstraintLayout.LayoutParams(binding.myView.width, binding.myView.height)
//            layoutParams.leftToLeft = x.toInt()
//            layoutParams.leftMargin = x.toInt()
//            layoutParams.setMargins(x.toInt(), x.toInt(), x.toInt(), x.toInt())
//            binding.root.layoutParams = layoutParams
//            binding.root.requestLayout()
//            binding.root.invalidate()
            return super.onScroll(e1, e2, distanceX, distanceY)
        }
    }

    private fun setMargins(view: View, left: Int, top: Int, right: Int, bottom: Int) {
        if (view.layoutParams is MarginLayoutParams) {
            val p = view.layoutParams as MarginLayoutParams
            p.setMargins(left, top, right, bottom)
            view.requestLayout()
        }
    }

    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrimBinding.inflate(layoutInflater)
        setContentView(binding.root)
        gestureDetector = GestureDetector(this, gestureListener)
//
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uriList ->
            val imageUriList = mutableListOf<String>()
            uriList.forEach {
                imageUriList += it.toString()
            }
            binding.rvTrim.bindingAdapter.models = imageUriList
        }.launch("image/*")

        binding.rvTrim.setup {
            addType<String>(R.layout.item_frame)
        }.models = null
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }
}