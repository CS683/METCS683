package edu.bu.graphicexample

import androidx.appcompat.app.AppCompatActivity
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.LinearLayout
import android.content.Intent
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView

class AnimationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        //set up the animation through xml file
        findViewById<ImageView>(R.id.chatimageview).setOnClickListener{
            val hyperspaceJump = AnimationUtils.loadAnimation(this, R.anim.animateexample)
            it.startAnimation(hyperspaceJump)
        }

        val animationIV = ImageView(this)
        animationIV?.setBackgroundResource(R.drawable.animationlist)
        val frameAnimation = animationIV!!.background as AnimationDrawable

        //  frameAnimation.start();
        findViewById<LinearLayout>(R.id.animationlayoutout).addView(animationIV)

        animationIV.setOnClickListener {
            // the animation will start after 1s
            val animationHandler = Handler(this.mainLooper)
            animationHandler.postDelayed({ frameAnimation!!.start() }, 1000)

            //the animation will stop after 6s
            animationHandler.postDelayed({ frameAnimation!!.stop() }, 6000)

        }
    }

    fun nextScreen(v: View?) {
        startActivity(Intent(this, CanvasActivity::class.java))
    }
}