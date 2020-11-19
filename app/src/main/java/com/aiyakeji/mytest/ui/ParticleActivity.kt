package com.aiyakeji.mytest.ui

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.aiyakeji.mytest.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_particle.*

/**
 * @author CWQ
 * @date 2020/10/20
 */
class ParticleActivity : AppCompatActivity() {

    lateinit var rotateAnimator: ObjectAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_particle)

        initImage()

    }


    private fun initImage() {
        Glide.with(this).load(R.mipmap.girl1)
                .circleCrop()
                .into(music_pic)

        rotateAnimator = ObjectAnimator.ofFloat(music_pic, View.ROTATION, 0f, 360f)
        rotateAnimator.duration = 6000
        rotateAnimator.repeatCount = -1
        rotateAnimator.interpolator = LinearInterpolator()
        rotateAnimator.start()
    }
}