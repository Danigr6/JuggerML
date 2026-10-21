package ui.navigation

import androidx.annotation.StringRes
import com.example.juggerml.R

sealed class Screen(
    val route: String,
    @StringRes val title: Int
) {
    object Login: Screen("Login", R.string.titulo_LoginScreen)
    object Home: Screen("Home", R.string.titulo_HomeScreen)
    object Settings: Screen("Settings", R.string.titulo_SettingsScreen)
    object Register: Screen("Register", R.string.titulo_RegisterScreen)
}
