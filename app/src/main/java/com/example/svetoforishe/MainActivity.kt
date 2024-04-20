package com.example.svetoforishe

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.marginBottom
import com.example.svetoforishe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentLightIndex = 0
    private lateinit var lights: MutableList<ImageView>

    var cc: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.root)

        lights = mutableListOf()
        var prevImageViewId = binding.changeButton.id
        for (i in 0..2) {
            val imageView = ImageView(this)
            lights.add(imageView)
            imageView.id = View.generateViewId()
            imageView.layoutParams = ConstraintLayout.LayoutParams(resources.displayMetrics.widthPixels, 200)
            imageView.setImageResource(R.drawable.circle)
            imageView.setPadding(0, 20, 0, 0)

            binding.linear.addView(imageView)

            constraintSet.connect(imageView.id, ConstraintSet.TOP, prevImageViewId, ConstraintSet.BOTTOM)
            constraintSet.centerHorizontally(imageView.id, ConstraintSet.PARENT_ID)

            prevImageViewId = imageView.id
        }

        constraintSet.applyTo(binding.root)
        updateLights()

        binding.changeButton.setOnClickListener {
            changeLights()

        }
    }

    private fun changeLights() {

        if (!cc) {
            currentLightIndex++;
        }
        else{
            currentLightIndex--;
        }
        if (currentLightIndex == 2){
            cc = true
        }
        else if (currentLightIndex == 0)
            cc = false
        updateLights()
    }

    private fun updateLights() {
        lights.forEachIndexed { index, light ->
            if (index == currentLightIndex) {
                light.setColorFilter(when (index) {
                    0 -> getColor(R.color.red)
                    1 -> getColor(R.color.yellow)
                    2 -> getColor(R.color.green)
                    else -> getColor(R.color.gray)
                })
            } else {
                light.setColorFilter(getColor(R.color.gray))
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("currentLightIndex", currentLightIndex)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentLightIndex = savedInstanceState.getInt("currentLightIndex")
        updateLights()
    }
}