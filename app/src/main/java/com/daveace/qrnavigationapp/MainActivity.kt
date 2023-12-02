package com.daveace.qrnavigationapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.daveace.qrnavigationapp.ui.screen.QRNavigationScreen
import com.daveace.qrnavigationapp.ui.theme.QRNavigationAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QRNavigationAppTheme {
                QRNavigationScreen()
            }
        }
    }
}
