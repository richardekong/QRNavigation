package com.daveace.qrnavigationapp.ui.widget


import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.daveace.qrnavigationapp.R

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    barItems:List<BarItem> = BottomNavigationBarItems.barItems) {
    NavigationBar(
        contentColor = colorResource(id = R.color.md_theme_light_onPrimary),
        containerColor = colorResource(id = R.color.md_theme_light_onSecondaryContainer)
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        barItems.forEach { navItem ->
            val isSelected = currentRoute == navItem.route
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }

                },
                icon = {
                    Icon(
                        imageVector = navItem.image,
                        contentDescription = navItem.title,
                        tint = if (isSelected) {
                            colorResource(id = navItem.selectedBottomIconTint)
                        } else {
                            colorResource(id = navItem.unSelectedBottomIconTint)
                        }
                    )
                },
                label = {
                    Text(
                        text = navItem.title,
                        color = colorResource(id = navItem.color),
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    }
}