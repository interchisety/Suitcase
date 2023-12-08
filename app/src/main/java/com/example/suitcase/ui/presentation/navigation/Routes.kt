package com.example.suitcase.ui.presentation.navigation

import androidx.annotation.StringRes
import com.example.suitcase.R

interface AppDestination {
    val route: String
    val title: Int
    val requireUpNavigation: Boolean
}

object RootRoute: AppDestination {
    override val route: String = "splash"
    @StringRes
    override val title = R.string.home_screen_title
    override val requireUpNavigation = false
}

object OnboardRoute: AppDestination {
    override val route: String = "onboard"
    @StringRes
    override val title = R.string.home_screen_title
    override val requireUpNavigation = false
}

object LoginRoute: AppDestination {
    override val route: String = "login"
    @StringRes
    override val title = R.string.home_screen_title
    override val requireUpNavigation = false
}

object SignupRoute: AppDestination {
    override val route: String = "signup"
    @StringRes
    override val title = R.string.home_screen_title
    override val requireUpNavigation = false
}

object ForgotRoute: AppDestination {
    override val route: String = "forgot"
    @StringRes
    override val title = R.string.home_screen_title
    override val requireUpNavigation = false
}

object HomeRoute: AppDestination {
    override val route: String = "home"
    @StringRes
    override val title = R.string.home_screen_title
    override val requireUpNavigation = false
}

object ListRoute: AppDestination {
    override val route: String = "profile"
    @StringRes
    override val title = R.string.profile_screen_title
    override val requireUpNavigation = false
}

object AddListRoute: AppDestination {
    override val route: String = "addlist"
    @StringRes
    override val title = R.string.settings_screen_title
    override val requireUpNavigation = false
}

object AddItemRoute: AppDestination {
    override val route: String = "additem"
    @StringRes
    override val title = R.string.settings_screen_title
    override val requireUpNavigation = false
}

object NewListDetailsRoute: AppDestination {
    override val route: String = "newlistdetails"
    @StringRes
    override val title = R.string.settings_screen_title
    override val requireUpNavigation = false
}

object SettingsRoute: AppDestination {
    override val route: String = "settings"
    @StringRes
    override val title = R.string.settings_screen_title
    override val requireUpNavigation = false
}

object TestRoute: AppDestination {
    override val route: String = "test"
    @StringRes
    override val title = R.string.test_screen_title
    override val requireUpNavigation = false
}

object PageUnderSettingsRoute: AppDestination {
    override val route: String = "pageundersettings"
    @StringRes
    override val title = R.string.page_under_settings_screen_title
    override val requireUpNavigation = false
}