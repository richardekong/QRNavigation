package com.daveace.qrnavigationapp.route

import com.daveace.qrnavigationapp.route.RouteName.Companion.CONTENT
import com.daveace.qrnavigationapp.route.RouteName.Companion.HOME
import com.daveace.qrnavigationapp.route.RouteName.Companion.LOGIN
import com.daveace.qrnavigationapp.route.RouteName.Companion.SIGNUP
import com.daveace.qrnavigationapp.route.RouteName.Companion.SPACES_EXPLORER
import com.daveace.qrnavigationapp.route.RouteName.Companion.SPLASH

sealed class NavRoute(val route: String) {
    data object Home : NavRoute(HOME)
    data object Content : NavRoute(CONTENT)
    data object Login : NavRoute(LOGIN)
    data object Signup : NavRoute(SIGNUP)
    data object SpaceExplorer : NavRoute(SPACES_EXPLORER)
    data object Splash : NavRoute(SPLASH)

}

