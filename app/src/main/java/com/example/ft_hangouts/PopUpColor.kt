package com.example.ft_hangouts

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle

import android.view.View
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import java.text.SimpleDateFormat
import java.util.*

class PopUpColor: AppCompatActivity() {
    private lateinit var boxLight: CheckBox
    private lateinit var boxDark: CheckBox
    private lateinit var button: Button
    private var darkStatusBar = false
    private lateinit var popupWindowViewWithBorder:CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.popup_window)

        initView()
        supportActionBar!!.hide()
        button.setOnClickListener {
            val result: String
            if (boxLight.isChecked && boxDark.isChecked) {
                result = getString(R.string.no_both_changes)
            } else if (boxLight.isChecked) {
                result = getString(R.string.its_light)
                ColorManager.back = "#d9d9d9"
                ColorManager.text = "#004d4d"
            } else if (boxDark.isChecked) {
                result = getString(R.string.its_dark)
                ColorManager.back = "#004d4d"
                ColorManager.text = "#d9d9d9"
            } else {
                result = getString(R.string.no_changes)
            }
            Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
            onBackPressed()
        }

        if (Build.VERSION.SDK_INT in 19..20) {
            setWindowFlag(this, true)
        }

        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // If you want dark status bar, set darkStatusBar to true
            if (darkStatusBar) {
                this.window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
            this.window.statusBarColor = Color.TRANSPARENT
            setWindowFlag(this, false)
        }

        popupWindowViewWithBorder.alpha = 0f
        popupWindowViewWithBorder.animate().alpha(1f).setDuration(500).setInterpolator(
            DecelerateInterpolator()
        ).start()
    }

    override fun onStart() {
        super.onStart()
        if (BackgroundCheck.time != "null" && BackgroundCheck.backOn) {
            val time = BackgroundCheck.time
            Toast.makeText(this, getString(R.string.time) + " $time", Toast.LENGTH_SHORT).show()
            BackgroundCheck.backOn = false
        }
    }

    override fun onStop() {
        super.onStop()
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        BackgroundCheck.time = sdf.format(Date())
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
        boxLight = findViewById(R.id.light)
        boxDark = findViewById(R.id.dark)
        button = findViewById(R.id.popup_window_button)

        popupWindowViewWithBorder = findViewById(R.id.popup_window_view_with_border)
    }
}

abstract class ColorManager {
    companion object {
        var back: String = "#004d4d"
        var text: String = "#d9d9d9"
    }
}