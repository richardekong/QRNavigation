package com.daveace.qrnavigationapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.daveace.qrnavigationapp.screen.Home
import com.daveace.qrnavigationapp.screen.SignUp
import com.daveace.qrnavigationapp.ui.theme.QRNavigationAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QRNavigationAppTheme {
//                    SignUp()
                Home()

            }
        }
    }
}
