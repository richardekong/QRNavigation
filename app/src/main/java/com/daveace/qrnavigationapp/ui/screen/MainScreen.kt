package com.daveace.qrnavigationapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Scaffold
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.daveace.qrnavigationapp.R
import com.daveace.qrnavigationapp.data.Organization
import com.daveace.qrnavigationapp.model.QRNavigationViewModel
import com.daveace.qrnavigationapp.route.NavRoute
import com.daveace.qrnavigationapp.ui.widget.AppBarIcon
import com.daveace.qrnavigationapp.ui.widget.BarItem
import com.daveace.qrnavigationapp.ui.widget.BottomNavigationBar
import com.daveace.qrnavigationapp.ui.widget.NavigationBarItemTitle.Companion.HOME
import com.daveace.qrnavigationapp.ui.widget.NavigationBarItemTitle.Companion.LOGIN
import com.daveace.qrnavigationapp.ui.widget.NavigationBarItemTitle.Companion.SIGNUP
import com.daveace.qrnavigationapp.ui.widget.QRNavigationTopAppBar

@Composable
fun QRNavigationScreen() {

    val navController: NavHostController = rememberNavController()
    val viewModel = QRNavigationViewModel()
    //Todo: fix the next line by injecting the ViewModel properly
    val currentOrganization by remember {
        mutableStateOf(viewModel.organizations()[1])
    }

    Scaffold(
        topBar = {
            TopAppBar(navController, currentOrganization)
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                NavigationHost(navController = navController)
            }
        },
        //Todo: attach bottom app bar for specific screens latter
        bottomBar = {
            BottomAppNavigationBar(navController)
        }
    )

}

@Composable
private fun BottomAppNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    when (navBackStackEntry?.destination?.route) {
        "home", "login", "signup" -> BottomNavigationBar(
            navController = navController,
            barItems = listOf(
                BarItem(title = HOME, image = Icons.Filled.Home, route = NavRoute.Home.route),
                BarItem(title = SIGNUP, image = Icons.Filled.AccountCircle, route = NavRoute.Signup.route),
                BarItem(title = LOGIN, image = Icons.Filled.Lock, route = NavRoute.Login.route)
            )
        )
        else -> BottomNavigationBar(navController = navController)

    }

}

@Composable
private fun TopAppBar(navController: NavHostController, currentOrganization: Organization) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    when (navBackStackEntry?.destination?.route) {
        "home", "login", "signup" -> QRNavigationTopAppBar(navigationIcon = AppBarIcon(onClick = {
            navController.navigate(NavRoute.Home.route)
        }))

        else -> QRNavigationTopAppBar(
            title = currentOrganization.name,
            navigationIcon = AppBarIcon(
                //TODO: fix the next line latter to retrieve the current organization's logo url
                logoURL = currentOrganization.logoURL,
                description = stringResource(id = R.string.org_logo),
                onClick = {
                    navController.navigate(NavRoute.SpaceExplorer.route)
                }
            )
        )
    }
}

@Composable
private fun NavigationHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.Home.route
    ) {
        composable(route = NavRoute.Home.route) {
            Home(navController = navController)
        }
        composable(route = NavRoute.Content.route) {
            Content(navController = navController)
        }
        composable(route = NavRoute.Login.route) {
            Login(navController = navController)
        }
        composable(route = NavRoute.Signup.route) {
            SignUp(navController = navController)
        }
        composable(route = NavRoute.SpaceExplorer.route) {
            SpacesExplorer(navController = navController)
        }
        composable(route = NavRoute.Splash.route) {
            Splash()
        }
    }
}

