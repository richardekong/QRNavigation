package com.daveace.qrnavigationapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.daveace.qrnavigationapp.screen.Home
import com.daveace.qrnavigationapp.screen.SpacesExplorer
import com.daveace.qrnavigationapp.ui.theme.QRNavigationAppTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
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
