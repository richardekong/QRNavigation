package com.daveace.qrnavigationapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.daveace.qrnavigationapp.ui.screen.Home
import com.daveace.qrnavigationapp.ui.screen.SpacesExplorer
import com.daveace.qrnavigationapp.ui.theme.QRNavigationAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QRNavigationAppTheme {
//                Home()
                SpacesExplorer()
            }
        }
    }
}
