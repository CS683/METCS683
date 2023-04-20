package edu.bu.graphicexample

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.view.MotionEvent
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Build
import android.util.Log
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Button
import androidx.annotation.RequiresApi
import edu.bu.graphicexample.databinding.ActivityCanvasBinding

class CanvasActivity : AppCompatActivity() {
    private var myCanvas: CustomCanvasView? = null
    private var myOval: CustomDrawableView? = null

    private var _binding: ActivityCanvasBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCanvasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.clearCanvas.setEnabled(false)
    }

    fun drawOval(v: View?) {
        if (myOval != null) return

        //dynamically add a view that draw a custom shape
        myOval = CustomDrawableView(this)
        myOval!!.layoutParams = LinearLayout.LayoutParams( // width / height
            LinearLayout.LayoutParams.WRAP_CONTENT, 150
        )
        binding.canvasLinearLayout!!.addView(myOval)
        binding.drawOval.setEnabled(false)
    }

    fun addCanvas(v: View?) {
        if (myCanvas != null) return


        // dynamically add a view that set up a canvas to draw.
        myCanvas = CustomCanvasView(this)
        myCanvas!!.layoutParams = LinearLayout.LayoutParams( // width / height
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )
        binding.canvasLinearLayout!!.addView(myCanvas)
        myCanvas!!.setOnTouchListener {
                v, e -> (v as CustomCanvasView).myOnTouchEvent(e) }

        binding.clearCanvas!!.isEnabled = true
        binding.drawOval.setEnabled(false)
    }

    fun clearCanvas(v: View?) {
        myCanvas!!.clearCanvas()
    }


    internal class CustomDrawableView(context: Context?) :
        View(context) {
        override fun onDraw(canvas: Canvas) {
            createDrawable().draw(canvas)
           // drawWithPaint(canvas)
        }

        fun createDrawable(): ShapeDrawable {
            // draw a oval
            val mDrawable = ShapeDrawable(OvalShape())
            //the shape cannot be drawn without the bound set
            mDrawable.setBounds(10, 10, 610, 110)
            // the default color is black if not set
            mDrawable.paint.color = Color.GREEN
            return mDrawable
        }

        fun drawWithPaint(canvas: Canvas){
            // we can also use paint directly draw on canvas
            val myPaint = Paint(Paint.ANTI_ALIAS_FLAG);
            myPaint.color = Color.GREEN
            myPaint.style = Paint.Style.FILL
            canvas.drawOval(RectF(10.0f, 10.0f, 610.0f, 110.0f),
                myPaint);

        }
    }


    //adapted from https://examples.javacodegeeks.com/android/
    // core/graphics/canvas-graphics/android-canvas-example/
    internal class CustomCanvasView(context: Context?) : View(context) {
        private val mPath: Path
        private val mPaint: Paint
        private var mX = 0f
        private var mY = 0f
        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            canvas.drawPath(mPath, mPaint)
            mPaint.style = Paint.Style.STROKE
        }

        private fun startTouch(x: Float, y: Float) {
            mPath.moveTo(x, y)
            mX = x
            mY = y
        }

        private fun upTouch() {
            mPath.lineTo(mX, mY)
        }

        private fun moveTouch(x: Float, y: Float) {
            val dx = Math.abs(x - mX)
            val dy = Math.abs(x - mY)
            if (dx >= 5 || dy >= 5) {
                mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
                mX = x
                mY = y
            }
        }

        fun clearCanvas() {
            mPath.reset()
            invalidate()
        }

        fun myOnTouchEvent(event: MotionEvent): Boolean {
            val x = event.x
            val y = event.y
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startTouch(x, y)
                    invalidate()
                    Log.d("Canvas", "down $x $y")
                }
                MotionEvent.ACTION_MOVE -> {
                    moveTouch(x, y)
                    invalidate()
                    Log.d("Canvas", "move $x $y")
                }
                MotionEvent.ACTION_UP -> {
                    upTouch()
                    invalidate()
                    Log.d("Canvas", "up $x $y")
                }
            }
            return true
        }

        init {
            mPath = Path()
            mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            mPaint.color = Color.RED
            mPaint.strokeWidth = 4f
        }
    }
}