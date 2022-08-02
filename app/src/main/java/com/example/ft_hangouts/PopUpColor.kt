package com.example.ft_hangouts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class PopUpColor: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.popup_window)

        // ...
    }
}