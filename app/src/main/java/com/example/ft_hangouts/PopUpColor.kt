package com.example.ft_hangouts

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.ColorUtils
import java.text.SimpleDateFormat
import java.util.*

class PopUpColor: AppCompatActivity() {
    private lateinit var boxLight: CheckBox
    private lateinit var boxDark: CheckBox
    private lateinit var button: Button
    private var darkStatusBar = false
    private lateinit var popup_window_view_with_border:CardView
    private lateinit var popup_window_view:CardView
    private lateinit var popup_window_background:ConstraintLayout
    private var time:String = "null"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.popup_window)

        initView()
        supportActionBar!!.hide()
        button.setOnClickListener {
            val result: String
            if (boxLight.isChecked && boxDark.isChecked) {
                result = "You cannot have both - no changes"
            } else if (boxLight.isChecked) {
                result = "Light it is"
              //  ColorManager.back = "#d9d9d9"
                ColorManager.back = "#d9d9d9"
                ColorManager.text = "#004d4d"
            } else if (boxDark.isChecked) {
                result = "Dark it is"
                ColorManager.back = "#004d4d"
                ColorManager.text = "#d9d9d9"
            } else {
                result = "No changes"
            }
            Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
            onBackPressed()
        }

        if (Build.VERSION.SDK_INT in 19..20) {
            setWindowFlag(this, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // If you want dark status bar, set darkStatusBar to true
                if (darkStatusBar) {
                    this.window.decorView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
                this.window.statusBarColor = Color.TRANSPARENT
                setWindowFlag(this, false)
            }
        }
        popup_window_view_with_border.alpha = 0f
        popup_window_view_with_border.animate().alpha(1f).setDuration(500).setInterpolator(
            DecelerateInterpolator()
        ).start()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        time = sdf.format(Date())
    }

    override fun onResume() {
        super.onResume()
        if (time != "null")
            Toast.makeText(this, "Last used : $time", Toast.LENGTH_SHORT).show()
    }

    private fun setWindowFlag(activity: Activity, on: Boolean) {
        val win = activity.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        } else {
            winParams.flags = winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()
        }
        win.attributes = winParams
    }

    private fun initView() {
        boxLight = findViewById<CheckBox>(R.id.light)
        boxDark = findViewById<CheckBox>(R.id.dark)
        button = findViewById<Button>(R.id.popup_window_button)
        popup_window_view = findViewById<CardView>(R.id.popup_window_view)
        popup_window_view_with_border = findViewById<CardView>(R.id.popup_window_view_with_border)
        popup_window_background = findViewById<ConstraintLayout>(R.id.popup_window_background)
    }
}

abstract class ColorManager(){
    companion object {
        var back: String = "#004d4d"
        var text: String = "#d9d9d9"
    }
}