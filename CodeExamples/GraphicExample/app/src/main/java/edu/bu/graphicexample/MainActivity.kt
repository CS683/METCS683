package edu.bu.graphicexample

import androidx.appcompat.app.AppCompatActivity
import android.widget.LinearLayout
import android.os.Bundle
import android.widget.ImageButton
import android.content.Intent
import android.graphics.drawable.TransitionDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    private var mLinearLayout: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ImageButton>(R.id.nextscreen).setOnClickListener{
                startActivity(Intent(this, AnimationActivity::class.java))
        }
    }

    fun changeBackground(v: View?) {
        val textView = findViewById<TextView>(R.id.textViewid)
        val imageBtn = findViewById<ImageButton>(R.id.imgBtnid)
        val drawable = imageBtn.drawable as TransitionDrawable

        if (textView.background == null){
            textView.setBackgroundResource(R.drawable.background)
            drawable.startTransition(3000)
        }else {
            textView.setBackgroundResource(0)
            drawable.reverseTransition(3000)
        }
    }

    fun addImageView(v: View?) {
        // Instantiate an ImageView and define its properties
        val iV = ImageView(this)
        iV.setImageResource(R.drawable.human)
        iV.adjustViewBounds = true // set the ImageView bounds to match Drawable 's dim’s
        iV.layoutParams = LinearLayout.LayoutParams( // width / height
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )
        // Add the ImageView to the layout and set the layout as the content view
        findViewById<LinearLayout>(R.id.linearlayout)?.addView(iV)
     //   Glide.with(this).load(R.drawable.human).override(600,400).into(iV)
    }
}