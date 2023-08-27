package com.example.shayari

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import java.util.Objects

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val anim= findViewById<LottieAnimationView>(R.id.splash_animation)


        Handler().postDelayed(
            {
                anim.visibility= View.VISIBLE
                anim.playAnimation()
                startActivity(Intent(this,MainActivity::class.java))
                finish()

            },3000
        )

    }
}