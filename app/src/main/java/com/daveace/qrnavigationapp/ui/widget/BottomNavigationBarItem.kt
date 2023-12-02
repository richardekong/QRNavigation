package com.daveace.qrnavigationapp.ui.widget

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector
import com.daveace.qrnavigationapp.R
import com.daveace.qrnavigationapp.route.RouteName.Companion.CONTENT
import com.daveace.qrnavigationapp.route.RouteName.Companion.HOME
import com.daveace.qrnavigationapp.route.RouteName.Companion.SPACES_EXPLORER

object BottomNavigationBarItems{
    val barItems = listOf(
        BarItem(
            title = NavigationBarItemTitle.HOME,
            image = Icons.Filled.Home,
            route = HOME
        ),
        BarItem(
            title = NavigationBarItemTitle.SPACE,
            image = Icons.Filled.Place,
            route = SPACES_EXPLORER
        ),
        BarItem(
            title = NavigationBarItemTitle.CONTENT,
            image = Icons.Filled.Info,
            route = CONTENT
        )
    )

}
interface NavigationBarItemTitle{
    companion object {
        const val HOME = "Home"
        const val SPACE = "Space"
        const val CONTENT = "Content"
        const val LOGIN = "Login"
        const val SIGNUP = "Signup"
    }
}
data class BarItem(
    val title:String,
    val image:ImageVector,
    val route:String,
    val selectedBottomIconTint: Int = R.color.md_theme_light_onSecondaryContainer,
    val unSelectedBottomIconTint: Int = R.color.md_theme_light_onPrimary,
    val color:Int = R.color.md_theme_light_onPrimary
)

